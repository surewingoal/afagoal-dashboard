package com.afagoal.web.login;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.constant.BaseConstant;
import com.afagoal.entity.system.SysFunction;
import com.afagoal.entity.system.SysUser;
import com.afagoal.security.SecurityContext;
import com.afagoal.service.sys.FunctionService;
import com.afagoal.utildto.Tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by BaoCai on 18/1/21.
 * Description:
 */
@Controller
public class LoginController {

    @Autowired
    private FunctionService functionService;

    @RequestMapping({"/index","/"})
    @BehaviorLog("用户首页")
    String index(Model model) {
        SysUser user = SecurityContext.currentUser();
        List<Tree<SysFunction>> menus;
        if(BaseConstant.SUPERADMIN.equals(user.getUserName())){
            menus = functionService.treeFunction();
        }else{
            menus = functionService.userFunction(user.getId());
        }
        model.addAttribute("menus", menus);
        model.addAttribute("name", user.getRealName());
        model.addAttribute("picUrl","/img/photo_s.jpg");
        model.addAttribute("username", user.getUserName());
        return "index";
    }

    @RequestMapping({ "/main" })
    public String main(){
        return "main";
    }

    @RequestMapping({"/login"})
    public String login(@RequestParam(value = "authentication_error" ,required = false) Boolean error){
        return "login";
    }
}
