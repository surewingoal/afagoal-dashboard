package com.afagoal.security;

import com.afagoal.entity.system.SysUser;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by BaoCai on 18/2/26.
 * Description:
 */
public class SecurityContext {

    private final static String ANONYMOUS_USER = "anonymousUser";

    public static SysUser currentUser() {
        Object userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userObject);
        AfagoalUser user;
        if (userObject instanceof AfagoalUser) {
            user = (AfagoalUser) userObject;
        } else if (userObject instanceof String && ANONYMOUS_USER.equals(userObject)) {
            return emptyUser();
        } else {
            throw new RuntimeException("unknown type of user!");
        }

        SysUser sysUser = new SysUser();
        sysUser.setId(user.getId());
        sysUser.setUserName(user.getUsername());
        return sysUser;
    }

    private static SysUser emptyUser() {
        SysUser sysUser = new SysUser();
        sysUser.setId(Integer.valueOf(-1));
        sysUser.setUserName(ANONYMOUS_USER);
        return sysUser;
    }

}
