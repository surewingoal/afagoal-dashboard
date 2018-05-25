package com.afagoal.service.sys;

import com.afagoal.dao.system.SysUserDao;
import com.afagoal.dao.system.SysUserRoleDao;
import com.afagoal.entity.system.SysUser;
import com.afagoal.entity.system.SysUserRole;
import com.afagoal.security.AfagoalPasswordEncoder;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;

/**
 * Created by BaoCai on 18/5/25.
 * Description:
 */
@Service
public class UserService {

    private static final Integer DEFAULT_USER_ROLE_ID = 2;

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Transactional
    public void createUser(SysUser user) {
        Assert.notNull(user, "用户信息不可为空！");
        String password = AfagoalPasswordEncoder.secrecy(user.getPassword());
        user.setPassword(password);
        if (null == user.getId()) {
            sysUserDao.save(user);
        } else {
            sysUserDao.merge(user);
        }
        createDefaultRole(user);
    }

    private void createDefaultRole(SysUser user) {
        List<BooleanExpression> list = new ArrayList();
        list.add(sysUserRoleDao.getQEntity().userId.eq(user.getId()));
        if (sysUserRoleDao.getCount(list) > 0) {
            return;
        }
        SysUserRole userRole = new SysUserRole();
        userRole.setRoleId(DEFAULT_USER_ROLE_ID);
        userRole.setUserId(user.getId());
        sysUserRoleDao.save(userRole);
    }

}
