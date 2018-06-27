package com.afagoal.web.login;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.auth.AuthenticationStores;
import com.afagoal.entity.system.SysUser;
import com.afagoal.security.AfagoalPasswordEncoder;
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
 * Description:
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
            return Response.ok("请填写用密码！");
        }
        password = AfagoalPasswordEncoder.secrecy(password);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        try {
            Authentication authentication = authenticationManager.authenticate(authRequest);

            if (null == authentication) {
                return Response.ok("用户不存在！");
            }

            String tokenId = UUID.randomUUID().toString().replace("-", "");
            authenticationStores.saveAuthentication(tokenId, authentication);
            Map result = new HashMap();
            result.put("afagoal_token", tokenId);
            result.put("user", authentication.getPrincipal());
            return Response.ok(result);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Response.ok("用户名密码错误！");
        }
    }

    @PostMapping("/afagoal_token/register")
    @ResponseBody
    @BehaviorLog("移动端用户注册")
    public Response registerCommit(@RequestBody SysUser user) {
        userService.createUser(user);
        return Response.ok("注册成功！");
    }

}
