package com.afagoal.service.sys;

import com.afagoal.dao.system.SysDeptDao;
import com.afagoal.entity.system.SysDept;
import com.afagoal.utils.TreeUtils;
import com.afagoal.utildto.Tree;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by BaoCai on 18/2/24.
 * Description:
 */
@Service
public class DeptService {

    @Autowired
    private SysDeptDao sysDeptDao;

    public Tree<SysDept> deptTree() {
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(sysDeptDao.getQEntity().state.ne((byte) 99));
        List<SysDept> depts = sysDeptDao.getEntities(booleanExpressions,null);

        List<Tree<SysDept>> trees = new ArrayList();
        depts.forEach(dept -> {

            Tree<SysDept> tree = new Tree();
            tree.setId(dept.getId().toString());
            tree.setParentId(dept.getPid().toString());
            tree.setText(dept.getDeptName());
            Map<String, Object> state = new HashMap();
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);

        });
        Tree<SysDept> t = TreeUtils.build(trees);
        return t;
    }
}
