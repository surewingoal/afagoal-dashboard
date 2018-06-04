package com.afagoal.service.token;

import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenTopPercentageDao;
import com.afagoal.entity.blockchain.TokenTopPercentage;
import com.querydsl.core.types.dsl.BooleanExpression;

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
public class TokenTopPercentageService {

    @Autowired
    private TokenTopPercentageDao tokenTopPercentageDao;

    @Transactional
    public void mergeTokenTopPercentage(String tokenId) {
        LocalDateTime now = LocalDateTime.now();
        List<TokenTopPercentage> todayPercentages = tokenPercentageOfDay(now, tokenId);
        List<TokenTopPercentage> yesterdayPercentages = tokenPercentageOfDay(now.plusDays(-1), tokenId);
        todayPercentages = merge(todayPercentages, yesterdayPercentages);
        tokenTopPercentageDao.save(todayPercentages);
    }

    private List<TokenTopPercentage> merge(List<TokenTopPercentage> todayPercentages, List<TokenTopPercentage> yesterdayPercentages) {
        Map<Byte, TokenTopPercentage> oldPercentageMap = yesterdayPercentages.stream()
                .collect(Collectors.toMap(
                        oldPercentage -> oldPercentage.getTopType(),
                        oldPercentage -> oldPercentage
                ));
        todayPercentages.forEach(percentage -> {
            TokenTopPercentage oldPercentage = oldPercentageMap.get(percentage.getTopType());
            if (null != oldPercentage) {
                percentage.setYesterdayPercentage(oldPercentage.getPercentage());
            }
        });
        return todayPercentages;
    }

    private List<TokenTopPercentage> tokenPercentageOfDay(LocalDateTime now, String tokenId) {
        List<BooleanExpression> booleanExperssions = new ArrayList();
        booleanExperssions.add(tokenTopPercentageDao.getQEntity().statisticTime.between(now.plusDays(-1), now));
        booleanExperssions.add(tokenTopPercentageDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        booleanExperssions.add(tokenTopPercentageDao.getQEntity().tokenId.eq(tokenId));


        return tokenTopPercentageDao.getEntities(booleanExperssions, null);

    }


}
