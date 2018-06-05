package com.afagoal.dto.blockchain;

import com.afagoal.entity.blockchain.Token;
import com.afagoal.entity.blockchain.TokenExt;
import com.afagoal.entity.blockchain.TokenLink;
import com.afagoal.utils.json.CustomDateSerialize;
import com.afagoal.utils.num.NumUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

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

    private BigDecimal totalSupply;

    private Long holders;

    private Long transfers;

    private Byte decimals;

    private String country;

    private String highestPrice;

    private String lowestPrice;

    private Long highestTransaction;

    private Long lowestTransaction;

    @JsonSerialize(using = CustomDateSerialize.class)
    private LocalDate icoStartDate;

    @JsonSerialize(using = CustomDateSerialize.class)
    private LocalDate icoEndDate;

    private String icoPrice;

    private BigDecimal totalCap;

    private String totalRaised;

    private Byte weight;

    private Collection<TokenLink> tokenLinks;

    public static TokenDto instance(Token token) {
        if (null == token) {
            return null;
        }
        TokenDto dto = new TokenDto();
        dto.setId(token.getId());
        dto.setCountry(token.getCountry());
        dto.setDecimals(token.getDecimals());
        dto.setTokenCode(token.getTokenCode());
        dto.setTokenName(token.getTokenName());
        dto.setIcoStartDate(token.getIcoStartDate());
        dto.setIcoEndDate(token.getIcoEndDate());
        dto.setIcoPrice(token.getIcoPrice());
        dto.setTotalCap(token.getTotalCap());

        if (null != token.getTotalRaised()) {
            String totalRaised = NumUtils.moneyFormat(token.getTotalRaised());
            dto.setTotalRaised(totalRaised);
        }

        dto.setWeight(dto.getWeight());
        TokenExt tokenExt = token.getTokenExt();
        if (null != tokenExt) {
            dto.setHolders(tokenExt.getHolders());
            dto.setTransfers(tokenExt.getTransfers());
            if (null != tokenExt.getHighestPrice()) {
                String heightPrice =NumUtils.moneyFormat(tokenExt.getHighestPrice());
                dto.setHighestPrice(heightPrice);
            }
            if (null != tokenExt.getLowestPrice()) {
                String lowestPrice = NumUtils.moneyFormat(tokenExt.getLowestPrice());
                dto.setLowestPrice(lowestPrice);
            }
            dto.setHighestTransaction(tokenExt.getHighestTransaction());
            dto.setLowestTransaction(tokenExt.getLowestTransaction());
            dto.setTotalSupply(tokenExt.getTotalSupply());
        }
        Collection<TokenLink> links = token.getTokenLinks();
        if (!CollectionUtils.isEmpty(links)) {
            dto.setTokenLinks(links);
        }
        return dto;
    }


}
