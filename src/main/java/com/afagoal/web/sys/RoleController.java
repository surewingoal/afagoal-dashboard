package com.afagoal.web.sys;

import com.afagoal.dao.system.SysRoleDao;
import com.afagoal.entity.system.SysRole;
import com.afagoal.util.PageData;
import com.afagoal.util.Response;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by BaoCai on 18/2/11.
 * Description:
 */
@Controller
public class RoleController {

    @Autowired
    private SysRoleDao sysRoleDao;

    @RequestMapping("sys/role")
    public String roleList(){
        return "system/role/roles";
    }

    @RequestMapping("sys/role/info")
    public String roleInfo(@RequestParam(required = false) Integer id,ModelMap modelMap){
        if(null != id){
            SysRole sysRole = sysRoleDao.getById(id);
            modelMap.put("role",sysRole);
        }
        return "system/role/role_add";
    }

    @RequestMapping("sys/role/list")
    @ResponseBody
    public PageData roles(@RequestParam(required = false,value = "role_nName") String roleName,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize,
                          @RequestParam(defaultValue = "0",value = "offset") int pageNumber){
        List<BooleanExpression> booleanExpressions = new ArrayList();
        Pageable pageable = new PageRequest(pageNumber,pageSize);
        if(StringUtils.isNotEmpty(roleName)){
            booleanExpressions.add(sysRoleDao.getQEntity().roleName.like("%" + roleName +"%"));
        }
        booleanExpressions.add(sysRoleDao.getQEntity().state.ne((byte) 99));
        List<SysRole> roles = sysRoleDao.getEntities(booleanExpressions,pageable);
        Long total = sysRoleDao.getCount(booleanExpressions);
        return new PageData(roles,total.intValue());
    }

    @RequestMapping("sys/role/save")
    @ResponseBody
    @Transactional
    public Response save(SysRole role){
        Assert.notNull(role,"对象不可为空！");
        if(null == role.getId()){
            sysRoleDao.save(role);
        }else{
            sysRoleDao.merge(role);
        }
        return Response.ok("保存成功！");
    }

    @RequestMapping("sys/role/delete")
    @ResponseBody
    @Transactional
    public Response delete(String ids){
        if(StringUtils.isEmpty(ids)){
            return Response.error("请选择相应的记录！");
        }
        Set<Integer> idSet = Arrays.stream(ids.split(","))
                .map(id -> Integer.valueOf(id))
                .collect(Collectors.toSet());
        sysRoleDao.delete(idSet);
        return Response.ok("操作成功！");
    }

}
