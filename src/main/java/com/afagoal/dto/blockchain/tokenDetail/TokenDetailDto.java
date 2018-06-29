package com.afagoal.dto.blockchain.tokenDetail;

import com.afagoal.entity.blockchain.TokenDetail;
import com.afagoal.utils.json.CustomDateTimeSerialize;
import com.afagoal.utils.num.NumUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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

    private String priceChange;

    @JsonSerialize(using = CustomDateTimeSerialize.class)
    private LocalDateTime statisticTime;

    private String totalValue;

    private BigDecimal usdProfitability;  //usd盈利率

    private BigDecimal usd;  //usd盈利率

    private String usdStr;

    private BigDecimal ethProfitability;  //eth盈利率

    private String eth;

    private String circulatingSupply;  //今日流动量

    private Long todayTransaction;

    private BigDecimal transfersUsd;

    private BigDecimal transfersToken;

    public static TokenDetailDto instance(TokenDetail detail) {
        if (null == detail) {
            return null;
        }
        TokenDetailDto dto = new TokenDetailDto();
        dto.setTokenName(detail.getTokenName());
        dto.setTokenCode(detail.getTokenCode());
        dto.setId(detail.getId());
        dto.setTransfersToken(detail.getTransfersToken());
        dto.setTransfersUsd(detail.getTransfersUsd());
        dto.setUsd(detail.getUsd());
        if (null != detail.getCirculatingSupply()) {
            dto.setCirculatingSupply(NumUtils.moneyFormat(detail.getCirculatingSupply()));
        }
        if (null != detail.getTotalValue()) {
            dto.setTotalValue(NumUtils.moneyFormat(detail.getTotalValue()));
        }
        if (null != detail.getUsd()) {
            dto.setUsdStr(NumUtils.moneyFormat(detail.getUsd()));
        }
        if (null != detail.getEth()) {
            dto.setEth(NumUtils.moneyFormat(detail.getEth(), NumUtils.UNIT_ETH));
        }
        dto.setEthProfitability(detail.getEthProfitability());
        if (null != detail.getPriceChange()) {
            dto.setPriceChange(NumUtils.percentageFormat(detail.getPriceChange()));
        }
        dto.setTodayTransaction(detail.getTodayTransaction());
        dto.setUsdProfitability(detail.getUsdProfitability());
        dto.setStatisticTime(detail.getStatisticTime());
        return dto;
    }
}
