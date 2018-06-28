package com.afagoal.web.login;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.entity.system.SysUser;
import com.afagoal.exception.UserRegisteredException;
import com.afagoal.service.sys.UserService;
import com.afagoal.utildto.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by BaoCai on 18/5/25.
 * Description: user register
 */
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    @BehaviorLog("用户注册")
    public String registerPage() {
        return "register/register";
    }

    @PostMapping("/register/commit")
    @ResponseBody
    @BehaviorLog("用户注册提交")
    public Response registerCommit(SysUser user) {
        try {
            userService.createUser(user);
        } catch (UserRegisteredException e) {
            return Response.error(e.getMessage());
        }
        return Response.ok("注册成功！");
    }

}
