package com.afagoal.dto.wc;

import com.afagoal.entity.wc.WorldCupGuess;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/6/3.
 * Description:
 */
@Getter
@Setter
public class WorldGuessRequestDto {

    @JsonProperty("wechat_nick_name")
    private String wechatNickName;

    private String champion;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private Byte gender;

    private String city;

    private String province;

    private String language;

    private String country;

    private String mobile;
}
