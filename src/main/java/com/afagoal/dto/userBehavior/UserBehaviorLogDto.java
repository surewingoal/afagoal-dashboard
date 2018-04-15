package com.afagoal.dto.userBehavior;

import com.afagoal.entity.behavior.UserBehaviorLog;
import com.afagoal.utils.json.CustomDateTimeSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;


import lombok.Data;

/**
 * Created by BaoCai on 18/4/15.
 * Description:
 */
@Data
public class UserBehaviorLogDto {

    private Integer id;

    private String method;

    private Long usingTime;

    private Integer userId;

    private String userName;

    private String operation;

    private String operateIp;

    @JsonSerialize(using = CustomDateTimeSerialize.class)
    private LocalDateTime createdAt;

    public static UserBehaviorLogDto instance(UserBehaviorLog log) {
        UserBehaviorLogDto dto = new UserBehaviorLogDto();
        BeanUtils.copyProperties(log, dto);
        return dto;
    }
}
