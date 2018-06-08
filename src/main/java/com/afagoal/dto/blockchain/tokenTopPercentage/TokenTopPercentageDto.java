package com.afagoal.dto.blockchain.tokenTopPercentage;

import com.afagoal.entity.blockchain.TokenTopPercentage;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/6/8.
 * Description:
 */
@Getter
@Setter
public class TokenTopPercentageDto {


    public static TokenTopPercentageDto instance(TokenTopPercentage tokenTopPercentage){
        TokenTopPercentageDto dto = new TokenTopPercentageDto();

        return dto;
    }
}
