package com.afagoal.web.sys;

import com.afagoal.dao.system.SysDeptDao;
import com.afagoal.entity.system.SysDept;
import com.afagoal.util.PageData;
import com.afagoal.util.Response;
import com.afagoal.util.Tree;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BaoCai on 18/2/24.
 * Description:
 */
@Controller
public class DeptController {

    @Autowired
    private SysDeptDao sysDeptDao;

    @RequestMapping("/sys/dept")
    public String listPage() {
        return "system/dept/depts";
    }

    @RequestMapping("/sys/dept/info")
    public String infoPage(@RequestParam(value = "id", required = false) Integer id,
                           @RequestParam(value = "pid", required = false) Integer pid,
                           ModelMap modelMap) {
        if (null != pid && 0 != pid) {
            SysDept pDept = sysDeptDao.getById(pid);
            modelMap.put("pId", pDept.getId());
            modelMap.put("pName", pDept.getDeptName());
        } else {
            modelMap.put("pId", 0);
            modelMap.put("pName", "根目录");
        }


        if (null != id) {
            SysDept dept = sysDeptDao.getById(id);
            modelMap.put("dept", dept);

            if (dept != null && dept.getPid() != 0) {
                SysDept pDept = sysDeptDao.getById(Integer.valueOf(dept.getPid()));
                modelMap.put("pId", pDept.getId());
                modelMap.put("pName", pDept.getDeptName());
            }
        }

        return "system/dept/dept_add";
    }

    @RequestMapping(value = "/sys/dept/list", method = RequestMethod.GET)
    @ResponseBody
    public List<SysDept> listInfo(@RequestParam(value = "page" ,defaultValue = "0") Integer page,
                                      @RequestParam(value = "size" ,defaultValue = "1000000") Integer size) {
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(sysDeptDao.getQEntity().state.ne((byte)99));
        List<SysDept> depts = sysDeptDao.getEntities(booleanExpressions, new PageRequest(page,size));
        return depts;
    }

    @RequestMapping(value = "/sys/dept/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SysDept info(@PathVariable(value = "id") Integer id) {
        if (null == id) {
            throw new RuntimeException("请选择相应的记录！");
        }
        return sysDeptDao.getById(id);
    }

    @RequestMapping(value = "/sys/dept/save", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Response add(SysDept dept) {
        if (null != dept.getId()) {
            sysDeptDao.merge(dept);
        } else {
            sysDeptDao.save(dept);
        }
        return Response.ok("保存成功！");
    }

    @RequestMapping(value = "/sys/dept/delete",method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public Response delete(@RequestParam(value = "ids") String ids) {
        if (StringUtils.isEmpty(ids)) {
            return Response.error("请选择需要删除的记录！");
        }
        String[] idArr = ids.split(",");
        sysDeptDao.delete(idArr);
        return Response.ok("操作成功！");
    }


//    @GetMapping(value = "/sys/sysDeptDao/role_tree")
//    @ResponseBody
//    public Tree<SysDept> roleFunctionTree(@RequestParam(value = "role_id",required = false) Integer roleId){
//        Tree<SysDept> roleFunctionTree = deptService.roleFunctionTree(roleId);
//        return roleFunctionTree;
//    }

}
