package com.afagoal.dto.wc;

import com.afagoal.entity.wc.Advertisement;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/6/3.
 * Description:
 */
@Getter
@Setter
public class AdvertisementDto {

    private String content;

    private String img_url;

    private String advertise_link;

    private Byte type;

    public static AdvertisementDto instance(Advertisement advertisement){
        AdvertisementDto dto = new AdvertisementDto();
        BeanUtils.copyProperties(advertisement,dto);
        return dto;
    }
}
