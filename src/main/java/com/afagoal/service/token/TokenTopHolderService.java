package com.afagoal.service.token;

import com.afagoal.auxiliary.tokenEnum.TokenWatcherEnum;
import com.afagoal.auxiliary.tokenValueWatcher.ValueWatcherGenerator;
import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenTopHolderDao;
import com.afagoal.dao.blockchain.valueWatcher.ValueWatcherConditionDao;
import com.afagoal.dao.blockchain.valueWatcher.ValueWatcherDao;
import com.afagoal.dto.base.ValueDateModel;
import com.afagoal.dto.blockchain.TokenTopHolderSimpleDto;
import com.afagoal.dto.blockchain.tokenTopHolder.TokenTopHolderEchartDto;
import com.afagoal.entity.blockchain.TokenTopHolder;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcher;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcherCondition;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Created by BaoCai on 18/6/4.
 * Description:
 */
@Service
public class TokenTopHolderService {

    @Autowired
    private TokenTopHolderDao tokenTopHolderDao;
    @Autowired
    private ValueWatcherConditionDao watcherConditionDao;
    @Autowired
    private ValueWatcherDao valueWatcherDao;


    @Transactional
    public void mergeTopHolderInfo(String tokenId) {
        LocalDateTime now = LocalDateTime.now();
        List<TokenTopHolder> todayTops = tokenTopsOfDay(now, tokenId);
        List<TokenTopHolder> yesterdayTops = tokenTopsOfDay(now, tokenId);
        todayTops = merge(todayTops, yesterdayTops);
        tokenTopHolderDao.save(todayTops);
    }

    private List<TokenTopHolder> merge(List<TokenTopHolder> todayTops, List<TokenTopHolder> yesterdayTops) {
        Map<String, TokenTopHolder> oldTopMap = yesterdayTops.stream()
                .collect(Collectors.toMap(
                        top -> top.getAddress(),
                        top -> top
                ));
        todayTops.forEach(top -> {
            TokenTopHolder oldTop = oldTopMap.get(top.getAddress());
            if (null != oldTop) {
                BigDecimal percentageChange = top.getPercentage().subtract(oldTop.getPercentage());
                top.setYesterdayPercentage(oldTop.getPercentage());
                top.setYesterdayQuantily(oldTop.getQuantily());
                top.setYesterdayRank(oldTop.getRank());
                top.setPercentageChange(percentageChange);
            }
        });
        return todayTops;
    }

    private List<TokenTopHolder> tokenTopsOfDay(LocalDateTime now, String tokenId) {
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(tokenTopHolderDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        booleanExpressions.add(tokenTopHolderDao.getQEntity().tokenId.eq(tokenId));
        booleanExpressions.add(tokenTopHolderDao.getQEntity().statisticTime.between(now.plusDays(-1), now));
        return tokenTopHolderDao.getEntities(booleanExpressions, null);
    }

    public List<TokenTopHolderEchartDto> echartTopHolders(String tokenId, String address) {
        List<BooleanExpression> booleanExpressionList = new ArrayList();
        booleanExpressionList.add(tokenTopHolderDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        booleanExpressionList.add(tokenTopHolderDao.getQEntity().tokenId.eq(tokenId));
        booleanExpressionList.add(tokenTopHolderDao.getQEntity().address.eq(address));

        List<OrderSpecifier> orders = new ArrayList();
        orders.add(tokenTopHolderDao.getQEntity().statisticTime.asc());

        List<TokenTopHolder> tokenTopHolders = tokenTopHolderDao.getEntities(booleanExpressionList, orders, null);

        return tokenTopHolders.stream()
                .map(tokenTopHolder -> TokenTopHolderEchartDto.instance(tokenTopHolder))
                .collect(Collectors.toList());
    }

    @Transactional
    public void watchTopHolder(TokenTopHolderSimpleDto addressDto) {

        List<ValueDateModel> historyValues = tokenTopHolderDao.valueDateModels(ValueWatcherGenerator.WATCHER_SIZE, addressDto.getId(), addressDto.getAddress());
        if (CollectionUtils.isEmpty(historyValues)) {
            return;
        }
        List<ValueWatcherCondition> conditions = watcherConditionDao.getByWatcherType(TokenWatcherEnum.TOKEN_TOP_HOLDER.getWatcherType());
        ValueDateModel todayValue = historyValues.remove(0);

        for (ValueWatcherCondition condition : conditions) {
            ValueDateModel needWatch = TokenWatcherEnum.TOKEN_TOP_HOLDER.getWatcherMatch()
                    .match(todayValue.getValue(), historyValues, condition.getChangeRank(), condition.getCompareTimes());
            if (null != needWatch) {
                ValueWatcher valueWatcher = ValueWatcherGenerator.createValueWatcher(needWatch, todayValue, condition, addressDto, TokenWatcherEnum.TOKEN_VALUE);
                valueWatcherDao.save(valueWatcher);
                break;
            }
        }

    }

    public List<TokenTopHolder> todayTopHolders(String tokenId) {

        if (StringUtils.isEmpty(tokenId)) {
            throw new RuntimeException("请指定当前token!");
        }

        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(tokenTopHolderDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        booleanExpressions.add(tokenTopHolderDao.getQEntity().tokenId.eq(tokenId));
        booleanExpressions.add(tokenTopHolderDao.getQEntity().statisticTime
                .between(LocalDateTime.now().plusDays(-1), LocalDateTime.now()));

        List<OrderSpecifier> orders = new ArrayList();
        orders.add(tokenTopHolderDao.getQEntity().rank.asc());

        return tokenTopHolderDao.getEntities(booleanExpressions, orders, null);
    }

}
