package com.afagoal.service.token;

import com.afagoal.auxiliary.tokenEnum.TokenWatcherEnum;
import com.afagoal.auxiliary.tokenValueWatcher.ValueWatcherGenerator;
import com.afagoal.mail.AfagoalMainSender;
import com.afagoal.dao.blockchain.userFollow.TokenUserFollowDao;
import com.afagoal.dao.blockchain.valueWatcher.ValueWatcherConditionDao;
import com.afagoal.dao.blockchain.valueWatcher.ValueWatcherDao;
import com.afagoal.dto.base.ValueDateModel;
import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenDetailDao;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.entity.blockchain.TokenDetail;
import com.afagoal.entity.blockchain.userFollow.TokenUserFollow;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcher;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcherCondition;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;

/**
 * Created by BaoCai on 18/6/4.
 * Description:
 */
@Service
public class TokenDetailService {

    private static final int WATCHER_SIZE = 30;
    private static final String TOKEN_PRICE_CHANGE_SUBJECT = "AFAGOAL币种价格波动";

    @Autowired
    private TokenDetailDao tokenDetailDao;
    @Autowired
    private ValueWatcherConditionDao watcherConditionDao;
    @Autowired
    private ValueWatcherDao valueWatcherDao;
    @Autowired
    private TokenUserFollowDao tokenUserFollowDao;
    @Autowired
    private AfagoalMainSender afagoalMainSender;


    @Transactional
    public void mergeDetails(List<String> tokenIds, Pageable pageable) {
        List<TokenDetail> todayDetails = detailsOfOneDay(LocalDateTime.now(), tokenIds, pageable);
        List<TokenDetail> yesterdayDetails = detailsOfOneDay(LocalDateTime.now().plusDays(-1), tokenIds, pageable);
        todayDetails = merge(todayDetails, yesterdayDetails);
        tokenDetailDao.save(todayDetails);
    }

    private List<TokenDetail> merge(List<TokenDetail> todayDetails, List<TokenDetail> yesterdayDetails) {
        Map<String, TokenDetail> oldDetailMap = yesterdayDetails.stream()
                .collect(
                        Collectors.toMap(
                                detail -> detail.getTokenId(),
                                detail -> detail
                        )
                );
        todayDetails.forEach(detail -> {
            TokenDetail oldDetail = oldDetailMap.get(detail.getTokenId());
            if (oldDetail != null) {
                detail.setTodayTransaction(detail.getTransfers() - oldDetail.getTransfers());
                detail.setCirculatingSupply(detail.getTransfersToken().subtract(oldDetail.getTransfersToken()));
            }
        });
        return todayDetails;
    }

    private List<TokenDetail> detailsOfOneDay(LocalDateTime now,
                                              List<String> tokenIds,
                                              Pageable pageable) {
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(tokenDetailDao.getQEntity().tokenId.in(tokenIds));
        booleanExpressions.add(tokenDetailDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        booleanExpressions.add(tokenDetailDao.getQEntity().statisticTime.between(now.plusDays(-1), now));
        return tokenDetailDao.getEntities(booleanExpressions, pageable);
    }

    @Transactional
    public void watchTokenValue(TokenSimpleDto token) {
        List<ValueDateModel> historyValues = tokenDetailDao.valueDateModels(WATCHER_SIZE, token.getId());
        if (CollectionUtils.isEmpty(historyValues)) {
            return;
        }
        List<ValueWatcherCondition> conditions = watcherConditionDao.getByWatcherType(TokenWatcherEnum.TOKEN_VALUE.getWatcherType());
        ValueDateModel todayValue = historyValues.remove(0);

        for (ValueWatcherCondition condition : conditions) {
            ValueDateModel needWatch = TokenWatcherEnum.TOKEN_VALUE.getWatcherMatch()
                    .match(todayValue.getValue(), historyValues, condition.getChangeRank(), condition.getCompareTimes());
            if (null != needWatch) {
                ValueWatcher valueWatcher = ValueWatcherGenerator.createValueWatcher(needWatch, todayValue, condition, token, TokenWatcherEnum.TOKEN_VALUE);
                valueWatcherDao.save(valueWatcher);
                break;
            }
        }
    }

    @Transactional
    public void noticeUser(ValueWatcher valueWatcher) {
        if (null == valueWatcher) {
            return;
        }
        List<TokenUserFollow> followUsers = tokenUserFollowDao.findByTokenId(valueWatcher.getTokenId());
        //TODO 群发
        if (!CollectionUtils.isEmpty(followUsers)) {
            followUsers.forEach(followUser -> {
                        try {
                            afagoalMainSender.send(valueWatcher.getRemindInfo(), followUser.getUser().getEmail(), TOKEN_PRICE_CHANGE_SUBJECT);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
    }
}
