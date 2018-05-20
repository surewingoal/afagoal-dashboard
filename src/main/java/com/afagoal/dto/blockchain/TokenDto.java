package com.afagoal.dto.blockchain;

import com.afagoal.entity.blockchain.Token;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/5/20.
 * Description:
 */
@Getter
@Setter
public class TokenDto {

    private String id;

    private String tokenName;

    private String tokenCode;

    private Long totalSupply;

    private Long holders;

    private Long transfers;

    private Byte decimals;

    private String contract;

    private String country;

    private String overview;

    private BigDecimal highestPrice;

    private BigDecimal lowestPrice;

    private Long highestTransaction;

    private Long lowestTransaction;

    private LocalDate icoStartDate;

    private LocalDate icoEndDate;

    private BigDecimal icoPrice;

    private BigDecimal totalCap;

    private BigDecimal totalRaised;

    private Byte weight;

    public static TokenDto instance(Token token) {
        TokenDto dto = new TokenDto();
        BeanUtils.copyProperties(token, dto);
        return dto;
    }

}
