package com.afagoal.web.login;

import com.afagoal.entity.system.SysFunction;
import com.afagoal.service.sys.FunctionService;
import com.afagoal.util.Tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by BaoCai on 18/1/21.
 * Description:
 */
@Controller
public class LoginController {

    @Autowired
    private FunctionService functionService;

    @RequestMapping({ "/index" })
    String index(Model model) {
        List<Tree<SysFunction>> menus = functionService.treeFunction();
        model.addAttribute("menus", menus);
        model.addAttribute("name", "afagoal");
        model.addAttribute("picUrl","/img/photo_s.jpg");
        model.addAttribute("username", "afagoal");
        return "index";
    }

    @RequestMapping({ "/main" })
    public String main(){
        return "main";
    }

}
