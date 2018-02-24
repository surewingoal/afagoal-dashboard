package com.afagoal.dto.sys;

import java.util.List;

import lombok.Data;

/**
 * Created by BaoCai on 18/2/24.
 * Description:
 */
@Data
public class RoleRequestDto {
    private Integer id;
    private String roleName;
    private String introduce;
    private List<Integer> functionIds;
}
