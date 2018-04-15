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
           return emptyUser();
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(user.getId());
        sysUser.setUserName(user.getUsername());
        return sysUser;
    }

    private static SysUser emptyUser(){
        SysUser sysUser = new SysUser();
        sysUser.setId(Integer.valueOf(-1));
        sysUser.setUserName("未知用户");
        return sysUser;
    }

}
