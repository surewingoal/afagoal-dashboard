package com.afagoal.dto.blockchain;

import com.afagoal.entity.blockchain.Token;
import com.afagoal.entity.blockchain.TokenExt;
import com.afagoal.entity.blockchain.TokenLink;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

    @JsonIgnore
    private static final DecimalFormat NUM_FORMAT = new DecimalFormat("#.00000");
    @JsonIgnore
    private static final String UNIT_USD = " USD";

    private String id;

    private String tokenName;

    private String tokenCode;

    private Long totalSupply;

    private Long holders;

    private Long transfers;

    private Byte decimals;

    private String country;

    private String highestPrice;

    private String lowestPrice;

    private Long highestTransaction;

    private Long lowestTransaction;

    private LocalDate icoStartDate;

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
            String totalRaised = NUM_FORMAT.format(token.getTotalRaised()) + UNIT_USD;
            dto.setTotalRaised(totalRaised);
        }

        dto.setWeight(dto.getWeight());
        TokenExt tokenExt = token.getTokenExt();
        if (null != tokenExt) {
            dto.setHolders(tokenExt.getHolders());
            dto.setTransfers(tokenExt.getTransfers());
            if (null != tokenExt.getHighestPrice()) {
                String heightPrice = NUM_FORMAT.format(tokenExt.getHighestPrice()) + UNIT_USD;
                dto.setHighestPrice(heightPrice);
            }
            if (null != tokenExt.getLowestPrice()) {
                String lowestPrice = NUM_FORMAT.format(tokenExt.getLowestPrice()) + UNIT_USD;
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
