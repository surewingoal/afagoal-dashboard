package com.afagoal.dto.blockchain.tokenTopPercentage;

import com.afagoal.entity.blockchain.TokenTopPercentage;
import com.afagoal.utils.date.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/6/9.
 * Description:
 */
@Getter
@Setter
public class TokenTopPercentageEchartDto {

    @JsonIgnore
    private static BigDecimal divide = new BigDecimal(1000 * 10);

    private String statisticTime;

    private BigDecimal percentage;

    private BigDecimal holdNums;

    public static TokenTopPercentageEchartDto instance(TokenTopPercentage percentage) {
        if (null == percentage) {
            return null;
        }
        TokenTopPercentageEchartDto dto = new TokenTopPercentageEchartDto();
        dto.setStatisticTime(DateUtils.format(percentage.getStatisticTime().toLocalDate()));
        dto.setHoldNums(percentage.getHoldNums().divide(divide));
        dto.setPercentage(percentage.getPercentage());
        return dto;
    }
}
