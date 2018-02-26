package com.afagoal.service.sys;

import com.afagoal.dao.system.SysFunctionDao;
import com.afagoal.dao.system.SysRoleFunctionDao;
import com.afagoal.entity.system.SysFunction;
import com.afagoal.entity.system.SysRoleFunction;
import com.afagoal.util.BuildTree;
import com.afagoal.util.Tree;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by BaoCai on 18/1/21.
 * Description:
 */
@Service
public class FunctionService {

    @Autowired
    private SysFunctionDao functionDao;
    @Autowired
    private SysRoleFunctionDao sysRoleFunctionDao;

    public List<Tree<SysFunction>> treeFunction(){
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(functionDao.getQEntity().state.ne((byte) 99));
        List<SysFunction> functions = functionDao.getEntities(booleanExpressions,null);

        return functionTree(functions);
    }

    public List<Tree<SysFunction>> userFunction(Integer userId){
        List<SysFunction> functions = functionDao.userFunction(userId);
        return functionTree(functions);
    }

    private List<Tree<SysFunction>> functionTree(List<SysFunction> functions){
        List<Tree<SysFunction>> trees = new ArrayList();

        functions.forEach(function -> {
            Tree<SysFunction> tree = new Tree();
            tree.setId(String.valueOf(function.getId()));
            tree.setParentId(String.valueOf(function.getPid()));
            tree.setText(function.getFunctionName());
            Map<String,Object> attributes = new HashMap();
            attributes.put("url",function.getFunctionUrl());
            attributes.put("icon",function.getIcon());
            tree.setAttributes(attributes);

            trees.add(tree);
        });
        return BuildTree.buildList(trees,"0");
    }

    public Tree<SysFunction> roleFunctionTree(Integer roleId) {
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(functionDao.getQEntity().state.ne((byte) 99));
        List<SysFunction> functions = functionDao.getEntities(booleanExpressions,null);

        List<Tree<SysFunction>> trees = new ArrayList();
        Set<Integer> functionIds = existFunctions(roleId,functions);
        functions.forEach(function -> {
            Tree<SysFunction> tree = new Tree();
            tree.setId(String.valueOf(function.getId()));
            tree.setParentId(String.valueOf(function.getPid()));
            tree.setText(function.getFunctionName());

            Map<String,Object> state = new HashMap();
            if(functionIds.contains(function.getId())){
                state.put("selected",true);
            }else{
                state.put("selected",false);
            }
            tree.setState(state);

            trees.add(tree);
        });

        Tree<SysFunction> tree = BuildTree.build(trees);

        return tree;
    }

    private Set<Integer> existFunctions(Integer roleId, List<SysFunction> functions) {
        if(null == roleId){
            return Collections.EMPTY_SET;
        }
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(sysRoleFunctionDao.getQEntity().roleId.eq(roleId));
        List<SysRoleFunction> roleFunctions = sysRoleFunctionDao.getEntities(booleanExpressions,null);
        Set<Integer> set = roleFunctions.stream()
                .map(roleFunction -> roleFunction.getFunctionId())
                .collect(Collectors.toSet());

        //父级目录
        functions.forEach(function -> {
            if(set.contains(function.getPid())){
                set.remove(function.getPid());
            }
        });

        return set;
    }
}
