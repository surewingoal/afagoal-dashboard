package com.afagoal.dto.blockchain;

import com.afagoal.entity.blockchain.TokenDetail;
import com.afagoal.utils.json.CustomDateTimeSerialize;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/5/20.
 * Description:
 */
@Getter
@Setter
public class TokenDetailDto {

    private String id;

    private String tokenName;

    private String tokenCode;

    private String tokenId;

    private BigDecimal todayPrice;

    private BigDecimal priceChange;

    private Long volume;

    @JsonSerialize(using = CustomDateTimeSerialize.class)
    private LocalDateTime statisticTime;

    private BigDecimal totalValue;

    private BigDecimal usdProfitability;  //usd盈利率

    private BigDecimal usd;

    private BigDecimal ethProfitability;  //eth盈利率

    private BigDecimal eth;

    private BigDecimal circulatingSupply;  //今日流动量

    private Long todayTransaction;

    public static TokenDetailDto instance(TokenDetail detail){
        TokenDetailDto dto = new TokenDetailDto();
        BeanUtils.copyProperties(detail,dto);
        return dto;
    }
}
