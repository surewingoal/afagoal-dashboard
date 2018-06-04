package com.afagoal.service.token;

import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenDetailDao;
import com.afagoal.entity.blockchain.TokenDetail;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by BaoCai on 18/6/4.
 * Description:
 */
@Service
public class TokenDetailService {

    @Autowired
    private TokenDetailDao tokenDetailDao;

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

}
