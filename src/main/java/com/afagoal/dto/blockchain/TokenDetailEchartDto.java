package com.afagoal.dto.blockchain;

import com.afagoal.entity.blockchain.TokenDetail;
import com.afagoal.utils.json.CustomDateTimeSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @JsonSerialize(using = CustomDateTimeSerialize.class)
    private LocalDateTime statisticTime;

    public static TokenDetailEchartDto instance(TokenDetail detail){
        if(null == detail){
            return null;
        }
        TokenDetailEchartDto dto = new TokenDetailEchartDto();
        dto.setId(detail.getId());
        dto.setStatisticTime(detail.getStatisticTime());
        dto.setUsd(detail.getUsd());
        return dto;
    }

}
