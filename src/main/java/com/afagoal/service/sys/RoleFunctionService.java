package com.afagoal.service.sys;

import com.afagoal.dao.system.SysRoleDao;
import com.afagoal.dao.system.SysRoleFunctionDao;
import com.afagoal.entity.system.SysRole;
import com.afagoal.entity.system.SysRoleFunction;
import com.afagoal.utils.TreeUtils;
import com.afagoal.utildto.Tree;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Created by BaoCai on 18/2/23.
 * Description:
 */
@Service
public class RoleFunctionService {

    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleFunctionDao sysRoleFunctionDao;

    public Tree<SysRole> roleTree() {
        List<BooleanExpression> list = new ArrayList();
        list.add(sysRoleDao.getQEntity().state.ne((byte) 99));
        List<SysRole> roles = sysRoleDao.getEntities(list,null);

        List<Tree<SysRole>> trees = roleTrees(roles);
        return TreeUtils.build(trees);
    }

    private List<Tree<SysRole>> roleTrees(List<SysRole> roles) {
        if(CollectionUtils.isEmpty(roles)){
            return Collections.EMPTY_LIST;
        }
        List<Tree<SysRole>> trees = new ArrayList();
        roles.forEach(role -> {
            Tree<SysRole> tree = new Tree();
            tree.setText(role.getRoleName());
            tree.setParentId(String.valueOf(0));
            tree.setId(String.valueOf(role.getId()));
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        });
        return trees;
    }

    @Transactional
    public void save(Integer roleId, List<Integer> functions) {
        List<SysRoleFunction> list = new ArrayList();

        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(sysRoleFunctionDao.getQEntity().roleId.eq(roleId));
        List<SysRoleFunction> existRoleFunctions =sysRoleFunctionDao.getEntities(booleanExpressions,null);
        Set<Integer> existFunctionIds = existRoleFunctions.stream()
                .map(roleFunction -> roleFunction.getFunctionId())
                .collect(Collectors.toSet());

        Set<Integer> functionSet = functions.stream()
                .map(function -> function)
                .collect(Collectors.toSet());
        functionSet.remove(0);
        functionSet.remove(-1);

        functionSet.forEach(function -> {
            if(!existFunctionIds.contains(Integer.valueOf(function))){
                SysRoleFunction roleFunction = new SysRoleFunction();
                roleFunction.setFunctionId(Integer.valueOf(function));
                roleFunction.setRoleId(roleId);
                list.add(roleFunction);
            }
        });

        sysRoleFunctionDao.save(list);
    }


    @Transactional
    public void delete(Integer roleId, List<Integer> functions) {
        List<Integer> functionList = functions.stream()
                .map(functionId -> Integer.valueOf(functionId))
                .collect(Collectors.toList());
        sysRoleFunctionDao.delete(roleId,functionList);
    }
}
