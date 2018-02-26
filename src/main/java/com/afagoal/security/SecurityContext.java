package com.afagoal.security;

import com.afagoal.entity.system.SysUser;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by BaoCai on 18/2/26.
 * Description:
 */
public class SecurityContext {

    public static SysUser currentUser(){
        AfagoalUser user = (AfagoalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(null == user){
           throw new RuntimeException("找不到用户！");
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(user.getId());
        sysUser.setUserName(user.getUsername());
        return sysUser;
    }

}
