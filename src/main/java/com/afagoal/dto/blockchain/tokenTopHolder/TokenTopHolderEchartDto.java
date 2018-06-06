package com.afagoal.dto.blockchain.tokenTopHolder;

import com.afagoal.entity.blockchain.TokenTopHolder;
import com.afagoal.utils.date.DateUtils;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/6/6.
 * Description:
 */
@Getter
@Setter
public class TokenTopHolderEchartDto {

    private String id;

    private Byte rank;

    private BigDecimal percentage;

    private BigDecimal quantily;

    private String statisticTime;

    public static TokenTopHolderEchartDto instance(TokenTopHolder tokenTopHolder) {
        if (null == tokenTopHolder) {
            return null;
        }
        TokenTopHolderEchartDto dto = new TokenTopHolderEchartDto();
        dto.setId(tokenTopHolder.getId());
        dto.setPercentage(tokenTopHolder.getPercentage());
        dto.setQuantily(tokenTopHolder.getQuantily());
        dto.setRank(tokenTopHolder.getRank());
        dto.setStatisticTime(DateUtils.format(tokenTopHolder.getStatisticTime().toLocalDate()));
        return dto;
    }
}
