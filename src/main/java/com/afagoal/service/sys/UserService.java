package com.afagoal.service.sys;

import com.afagoal.dao.system.SysUserDao;
import com.afagoal.dao.system.SysUserExtDao;
import com.afagoal.dao.system.SysUserRoleDao;
import com.afagoal.dto.sys.WechatUserRegisterDto;
import com.afagoal.entity.system.SysUser;
import com.afagoal.entity.system.SysUserExt;
import com.afagoal.entity.system.SysUserRole;
import com.afagoal.exception.UserRegisteredException;
import com.afagoal.security.MD5Utils;
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
    @Autowired
    private SysUserExtDao sysUserExtDao;

    @Transactional
    public void createUser(SysUser user) {
        Assert.notNull(user, "用户信息不可为空！");
        String password = MD5Utils.passwordSecurcy(user.getPassword());
        user.setPassword(password);
        saveUser(user);
    }

    private void saveUser(SysUser user) {
        checkUserExist(user);
        if (null == user.getId()) {
            sysUserDao.save(user);
        } else {
            sysUserDao.merge(user);
        }
        createDefaultRole(user);
    }

    private void checkUserExist(SysUser user) {
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(sysUserDao.getQEntity().userName.eq(user.getUserName()));
        long count = sysUserDao.getCount(booleanExpressions);
        if (count > 0) {
            throw new UserRegisteredException("该用户名已被被注册！");
        }
        booleanExpressions.clear();
        booleanExpressions.add(sysUserDao.getQEntity().mobile.eq(user.getMobile()));
        count = sysUserDao.getCount(booleanExpressions);
        if (count > 0) {
            throw new UserRegisteredException("该电话号码已被被注册！");
        }
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

    @Transactional
    public void createWechatUser(WechatUserRegisterDto userDto) {
        SysUser user = userDto.instanceUser();
        saveUser(user);

        SysUserExt userExt = userDto.instanceUserExt();
        userExt.setUserId(user.getId());

        sysUserExtDao.save(userExt);
    }
}
