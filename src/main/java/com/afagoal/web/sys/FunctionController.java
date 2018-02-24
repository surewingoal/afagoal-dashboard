package com.afagoal.web.sys;

import com.afagoal.dao.system.SysFunctionDao;
import com.afagoal.entity.system.SysFunction;
import com.afagoal.service.sys.FunctionService;
import com.afagoal.util.PageData;
import com.afagoal.util.Response;
import com.afagoal.util.Tree;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Created by BaoCai on 18/1/27.
 * Description:
 */
@Controller
public class FunctionController {

    @Autowired
    private SysFunctionDao sysFunctionDao;
    @Autowired
    private FunctionService functionService;

    @RequestMapping("/sys/function")
    public String listPage() {
        return "system/function/functions";
    }

    @RequestMapping("/sys/function/info")
    public String infoPage(@RequestParam(value = "id", required = false) Integer id,
                           @RequestParam(value = "pid", required = false) Integer pid,
                           ModelMap modelMap) {
        if (null != pid && 0 != pid) {
            SysFunction pFunction = sysFunctionDao.getById(pid);
            modelMap.put("pId", pFunction.getId());
            modelMap.put("pName", pFunction.getFunctionName());
        } else {
            modelMap.put("pId", 0);
            modelMap.put("pName", "根目录");
        }


        if (null != id) {
            SysFunction function = sysFunctionDao.getById(id);
            modelMap.put("function", function);

            if (function != null && function.getPid() != 0) {
                SysFunction pFunction = sysFunctionDao.getById(Integer.valueOf(function.getPid()));
                modelMap.put("pId", pFunction.getId());
                modelMap.put("pName", pFunction.getFunctionName());
            }
        }

        return "system/function/function_add";
    }

    @RequestMapping(value = "/sys/function/list", method = RequestMethod.GET)
    @ResponseBody
    public List<SysFunction> listInfo(@RequestParam(value = "page" ,defaultValue = "0") Integer page,
                                      @RequestParam(value = "size" ,defaultValue = "1000000") Integer size) {
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(sysFunctionDao.getQEntity().state.ne((byte)99));
        List<SysFunction> functions = sysFunctionDao.getEntities(booleanExpressions, new PageRequest(page,size));
        return functions;
    }

    @RequestMapping(value = "/sys/function/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SysFunction info(@PathVariable(value = "id") Integer id) {
        if (null == id) {
            throw new RuntimeException("请选择相应的记录！");
        }
        return sysFunctionDao.getById(id);
    }

    @RequestMapping(value = "/sys/function/save", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Response add(SysFunction function) {
        if (null != function.getId()) {
            sysFunctionDao.merge(function);
        } else {
            sysFunctionDao.save(function);
        }
        return Response.ok("保存成功！");
    }

    @RequestMapping(value = "/sys/function/delete",method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public Response delete(@RequestParam(value = "ids") String ids) {
        if (StringUtils.isEmpty(ids)) {
            return Response.error("请选择需要删除的记录！");
        }
        String[] idArr = ids.split(",");
        sysFunctionDao.delete(idArr);
        return Response.ok("操作成功！");
    }


    @RequestMapping(value = "/sys/function/page", method = RequestMethod.GET)
    @ResponseBody
    public PageData page(@RequestParam(value = "page" ,defaultValue = "0") Integer page,
                         @RequestParam(value = "function_name" ,required = false) String functionName,
                         @RequestParam(value = "size" ,defaultValue = "10") Integer size) {
        List<BooleanExpression> booleanExpressions = new ArrayList();
        if(!StringUtils.isEmpty(functionName)){
            booleanExpressions.add(sysFunctionDao.getQEntity().functionName.like("%" + functionName + "%"));
        }
        booleanExpressions.add(sysFunctionDao.getQEntity().state.ne((byte)99));
        List<SysFunction> functions = sysFunctionDao.getEntities(booleanExpressions, new PageRequest(page,size));
        Long count = sysFunctionDao.getCount(booleanExpressions);
        return new PageData(functions,count.intValue());
    }

    @GetMapping(value = "/sys/function/role_tree")
    @ResponseBody
    public Tree<SysFunction> roleFunctionTree(@RequestParam(value = "role_id",required = false) Integer roleId){
        Tree<SysFunction> roleFunctionTree = functionService.roleFunctionTree(roleId);
        return roleFunctionTree;
    }

}
