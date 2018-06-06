package com.afagoal.dto.blockchain;

import com.afagoal.entity.blockchain.TokenDetail;
import com.afagoal.utils.date.DateUtils;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/6/5.
 * Description:
 */
@Getter
@Setter
public class TokenDetailEchartDto {

    private String id;

    private BigDecimal usd;

    private String statisticTime;

    public static TokenDetailEchartDto instance(TokenDetail detail) {
        if (null == detail) {
            return null;
        }
        TokenDetailEchartDto dto = new TokenDetailEchartDto();
        dto.setId(detail.getId());
        dto.setStatisticTime(DateUtils.format(detail.getStatisticTime().toLocalDate()));
        dto.setUsd(detail.getUsd());
        return dto;
    }

}
