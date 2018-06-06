package com.afagoal.dto.blockchain.tokenTopHolder;

import com.afagoal.entity.blockchain.TokenTopHolder;
import com.afagoal.utils.json.CustomDateTimeSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/5/21.
 * Description:
 */
@Getter
@Setter
public class TokenTopHolderDto {

    private String id;

    private String tokenName;

    private String tokenCode;

    private String tokenId;

    private String address;

    private Byte rank;

    private BigDecimal quantily;

    private BigDecimal percentage;

    private Byte yesterdayRank;

    private BigDecimal yesterdayQuantily;

    private BigDecimal yesterdayPercentage;

    private BigDecimal percentageChange;

    @JsonSerialize(using = CustomDateTimeSerialize.class)
    private LocalDateTime statisticTime;

    public static TokenTopHolderDto instance(TokenTopHolder tokenTopHolder){
        TokenTopHolderDto dto = new TokenTopHolderDto();
        BeanUtils.copyProperties(tokenTopHolder,dto);
        return dto;
    }

}
