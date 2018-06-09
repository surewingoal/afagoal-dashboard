package com.afagoal.service.token;

import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenTopHolderDao;
import com.afagoal.dto.blockchain.tokenTopHolder.TokenTopHolderEchartDto;
import com.afagoal.entity.blockchain.TokenTopHolder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by BaoCai on 18/6/4.
 * Description:
 */
@Service
public class TokenTopHolderService {

    @Autowired
    private TokenTopHolderDao tokenTopHolderDao;

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

        List<TokenTopHolder> tokenTopHolders = tokenTopHolderDao.getEntities(booleanExpressionList, null);

        return tokenTopHolders.stream()
                .map(tokenTopHolder -> TokenTopHolderEchartDto.instance(tokenTopHolder))
                .collect(Collectors.toList());
    }
}
