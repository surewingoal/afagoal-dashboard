package com.afagoal.service.sys;

import com.afagoal.dao.system.SysFunctionDao;
import com.afagoal.entity.system.SysFunction;
import com.afagoal.util.BuildTree;
import com.afagoal.util.Tree;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BaoCai on 18/1/21.
 * Description:
 */
@Service
public class FunctionService {

    @Autowired
    private SysFunctionDao functionDao;

    public List<Tree<SysFunction>> treeFunction(){
        List<BooleanExpression> booleanExpressions = new ArrayList();
        List<SysFunction> functions = functionDao.getEntities(booleanExpressions,null);

        List<Tree<SysFunction>> trees = new ArrayList();

        functions.forEach(function -> {
            Tree<SysFunction> tree = new Tree();
            tree.setId(function.getId());
            tree.setParentId(function.getFunctionParentId());
            tree.setText(function.getFunctionName());
            Map<String,Object> attributrs = new HashMap();
            attributrs.put("url",function.getFunctionUrl());
            attributrs.put("icon",null);
            tree.setAttributes(attributrs);

            trees.add(tree);
        });
        return BuildTree.buildList(trees,"0");
    }

}
