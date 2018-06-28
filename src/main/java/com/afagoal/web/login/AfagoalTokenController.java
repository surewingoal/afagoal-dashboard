package com.afagoal.web.login;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.auth.AuthenticationStores;
import com.afagoal.dto.sys.WechatUserRegisterDto;
import com.afagoal.exception.UserRegisteredException;
import com.afagoal.security.AfagoalUser;
import com.afagoal.security.MD5Utils;
import com.afagoal.service.sys.UserService;
import com.afagoal.utildto.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by BaoCai on 18/5/26.
 * Description: 处理移动端登录
 */
@Controller
public class AfagoalTokenController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationStores authenticationStores;
    @Autowired
    private UserService userService;

    @RequestMapping("/tokens_online")
    @ResponseBody
    public Response onlineSession() {
        return Response.ok(authenticationStores.onlineAuthentications());
    }

    @RequestMapping(value = "/afagoal_token/login", method = RequestMethod.POST)
    @ResponseBody
    public Response tokenLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password) {
        if (StringUtils.isEmpty(username)) {
            return Response.ok("请填写用户名！");
        }
        if (StringUtils.isEmpty(password)) {
            return Response.ok("请填写密码！");
        }
        password = MD5Utils.passwordEncode(password);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        try {
            Authentication authentication = authenticationManager.authenticate(authRequest);

            if (null == authentication) {
                return Response.ok("用户不存在！");
            }

            String tokenId = UUID.randomUUID().toString().replace("-", "");
            authenticationStores.saveAuthentication(tokenId, authentication);
            AfagoalUser afagoalUser = (AfagoalUser) authentication.getPrincipal();
            Map result = new HashMap(afagoalUser.getDetails());
            result.put("afagoal_token", tokenId);
            return Response.ok(result);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Response.ok("用户名密码错误！");
        }
    }

    @PostMapping("/register/wechat")
    @ResponseBody
    @BehaviorLog("wechat端用户注册")
    public Response registerCommit(@RequestBody WechatUserRegisterDto user) {
        String validResult = user.valid();
        if (!StringUtils.isEmpty(validResult)) {
            return Response.error(validResult);
        }
        try {
            userService.createWechatUser(user);
        } catch (UserRegisteredException e) {
            return Response.error(e.getMessage());
        }
        return Response.ok("注册成功！");
    }

}
