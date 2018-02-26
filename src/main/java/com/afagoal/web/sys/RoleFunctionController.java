package com.afagoal.web.sys;

import com.afagoal.dao.system.SysRoleFunctionDao;
import com.afagoal.dto.sys.RoleFunctionRequestDto;
import com.afagoal.entity.system.SysFunction;
import com.afagoal.entity.system.SysRole;
import com.afagoal.entity.system.SysRoleFunction;
import com.afagoal.service.sys.RoleFunctionService;
import com.afagoal.utildto.PageData;
import com.afagoal.utildto.Response;
import com.afagoal.utildto.Tree;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by BaoCai on 18/2/23.
 * Description:
 */
@Controller
public class RoleFunctionController {

    @Autowired
    private RoleFunctionService roleFunctionService;
    @Autowired
    private SysRoleFunctionDao sysRoleFunctionDao;

    @RequestMapping("sys/role_function")
    public String roleFunctions() {
        return "system/role_function/role_function";
    }

    @RequestMapping("sys/role_function/add")
    public String add(@RequestParam(value = "role_id") Integer roleId,
                      ModelMap map) {
        map.put("roleId", roleId);
        return "system/role_function/role_function_add";
    }

    @RequestMapping("sys/role_function/tree")
    @ResponseBody
    public Tree<SysRole> roleTree() {
        Tree<SysRole> tree = roleFunctionService.roleTree();
        return tree;
    }

    @RequestMapping("sys/role_function/functions")
    @ResponseBody
    public PageData roles(@RequestParam(value = "role_id", required = false) Integer roleId,
                          @RequestParam(value = "page", defaultValue = "0") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (null == roleId) {
            return new PageData(null, 0);
        }
        List<BooleanExpression> list = new ArrayList();
        list.add(sysRoleFunctionDao.getQEntity().roleId.eq(roleId));
        List<SysRoleFunction> roleFunctions = sysRoleFunctionDao.getEntities(list, new PageRequest(page, size));
        Long count = sysRoleFunctionDao.getCount(list);

        List<SysFunction> functions = roleFunctions.stream()
                .map(roleFunction -> roleFunction.getFunction())
                .collect(Collectors.toList());

        return new PageData(functions, count.intValue());
    }

    @PostMapping("sys/role_function/save")
    @ResponseBody
    public Response save(RoleFunctionRequestDto roleFunctionRequestDto) {
        if (StringUtils.isEmpty(roleFunctionRequestDto.getFunctionIds())) {
            return Response.error("请选择授权的菜单！");
        }
        String[] functions = roleFunctionRequestDto.getFunctionIds().split(",");
        List<Integer> list = Arrays.asList(functions).stream()
                .map(function -> Integer.valueOf(function))
                .collect(Collectors.toList());
        roleFunctionService.save(roleFunctionRequestDto.getRoleId(),list);
        return Response.ok("操作成功！");
    }

    @PostMapping("sys/role_function/delete")
    @ResponseBody
    public Response delete(RoleFunctionRequestDto roleFunctionRequestDto) {
        if (StringUtils.isEmpty(roleFunctionRequestDto.getFunctionIds())) {
            return Response.error("请选择删除的菜单！");
        }
        if (null == roleFunctionRequestDto.getRoleId()) {
            return Response.error("请选择对应的角色！");
        }
        String[] functions = roleFunctionRequestDto.getFunctionIds().split(",");
        List<Integer> list = Arrays.asList(functions).stream()
                .map(function -> Integer.valueOf(function))
                .collect(Collectors.toList());
        roleFunctionService.delete(roleFunctionRequestDto.getRoleId(), list);
        return Response.ok("操作成功！");
    }

}
