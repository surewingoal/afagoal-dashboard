package com.afagoal.security;

import com.afagoal.dao.system.SysUserDao;
import com.afagoal.entity.system.SysUser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * Created by BaoCai on 18/2/26.
 * Description:
 */
public class AfagoalUserDetailsService implements UserDetailsService {

    private SysUserDao sysUserDao;

    private static List<GrantedAuthority> grantedAuthorities;

    public AfagoalUserDetailsService(SysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
        Assert.notNull(sysUserDao, "sysUserDao can't be null!");

        grantedAuthorities = new ArrayList();
        grantedAuthorities.add(new SimpleGrantedAuthority("BASE_USER"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserDao.getUserByName(username);
        if(null == user){
            throw new UsernameNotFoundException("用户不存在！");
        }
        return new AfagoalUser(user.getUserName(),user.getPassword(),grantedAuthorities,user.getId());
    }
}
