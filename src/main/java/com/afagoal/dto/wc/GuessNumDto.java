package com.afagoal.dto.wc;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/6/3.
 * Description:
 */
@Getter
@Setter
public class GuessNumDto {

    private String country;

    private Long guessNum;

    public static GuessNumDto instance(String country,Long guessNum){
        GuessNumDto dto = new GuessNumDto();
        dto.setCountry(country);
        dto.setGuessNum(guessNum);
        return dto;
    }

}
