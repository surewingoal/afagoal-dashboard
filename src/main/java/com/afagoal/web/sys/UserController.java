package com.afagoal.web.sys;

import com.afagoal.dao.system.SysUserDao;
import com.afagoal.entity.system.SysUser;
import com.afagoal.utildto.PageData;
import com.afagoal.utildto.Response;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by BaoCai on 18/2/24.
 * Description:
 */
@Controller
public class UserController {

    @Autowired
    private SysUserDao sysUserDao;

    @GetMapping("/sys/user")
    public String users() {
        return "/system/user/users";
    }

    @GetMapping("/sys/user/info")
    public String info(@RequestParam(value = "id", required = false) Integer id,
                       ModelMap modelMap) {
        if (!ObjectUtils.isEmpty(id)) {
            SysUser user = sysUserDao.getById(id);
            modelMap.put("user", user);
            if (null != user.getDept()) {
                modelMap.put("deptName", user.getDept().getDeptName());
            }
        }
        return "/system/user/user_add";
    }

    @GetMapping("/sys/user/list")
    @ResponseBody
    public PageData<SysUser> pageUsers(@RequestParam(required = false, value = "user_name") String userName,
                                       @RequestParam(required = false, value = "dept_id") Integer deptId,
                                       @RequestParam(defaultValue = "0", value = "page") int page,
                                       @RequestParam(defaultValue = "10", value = "size") int size) {
        List<BooleanExpression> booleanExpressionList = new ArrayList();
        booleanExpressionList.add(sysUserDao.getQEntity().state.ne((byte) 99));
        if (null != deptId) {
            booleanExpressionList.add(sysUserDao.getQEntity().deptId.eq(deptId));
        }
        if (StringUtils.isNotEmpty(userName)) {
            booleanExpressionList.add(sysUserDao.getQEntity().userName.like("%" + userName.trim() + "%").
                    or(sysUserDao.getQEntity().nickName.like("%" + userName.trim() + "%")));
        }
        List<OrderSpecifier> orderSpecifiers = new ArrayList();
        orderSpecifiers.add(sysUserDao.getQEntity().createdAt.desc());

        Pageable pageable = new PageRequest(page, size);

        List<SysUser> users = sysUserDao.getEntities(booleanExpressionList, orderSpecifiers, pageable);
        Long count = sysUserDao.getCount(booleanExpressionList);

        PageData pageData = new PageData(users, count.intValue());
        return pageData;
    }

    @PostMapping("/sys/user/save")
    @ResponseBody
    @Transactional
    public Response save(SysUser user) {
        Assert.notNull(user, "用户信息不可为空！");
        if (null == user.getId()) {
            sysUserDao.save(user);
        } else {
            sysUserDao.merge(user);
        }
        return Response.ok("保存成功！");
    }

    @DeleteMapping("/sys/user/delete")
    @ResponseBody
    @Transactional
    public Response delete(@RequestParam(value = "ids") String ids) {
        if (StringUtils.isEmpty(ids)) {
            return Response.error("请选择删除的记录！");
        }
        String[] idArray = ids.split(",");
        List<Integer> idList = Arrays.stream(idArray)
                .map(id -> Integer.valueOf(id))
                .collect(Collectors.toList());
        sysUserDao.delete(idList);
        return Response.ok("操作成功！");
    }

}
