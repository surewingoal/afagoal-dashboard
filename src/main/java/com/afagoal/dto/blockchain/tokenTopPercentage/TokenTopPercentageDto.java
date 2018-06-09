package com.afagoal.dto.blockchain.tokenTopPercentage;

import com.afagoal.entity.blockchain.TokenTopPercentage;
import com.afagoal.utils.json.CustomDateTimeSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/6/8.
 * Description:
 */
@Getter
@Setter
public class TokenTopPercentageDto {

    private String id;

    private String tokenName;

    private String tokenId;

    private String tokenCode;

    private Byte topType;

    private BigDecimal yesterdayPercentage;

    private BigDecimal percentage;

    @JsonSerialize(using = CustomDateTimeSerialize.class)
    private LocalDateTime statisticTime;

    private BigDecimal holdNums;

    public static TokenTopPercentageDto instance(TokenTopPercentage tokenTopPercentage) {
        if (null == tokenTopPercentage) {
            return null;
        }
        TokenTopPercentageDto dto = new TokenTopPercentageDto();
        BeanUtils.copyProperties(tokenTopPercentage, dto);
        return dto;
    }
}
