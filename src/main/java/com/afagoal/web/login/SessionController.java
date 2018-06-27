package com.afagoal.web.login;

import com.afagoal.security.AfagoalSecurityContextRepository;
import com.afagoal.utildto.Response;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by BaoCai on 18/5/26.
 * Description:
 */
@Controller
public class SessionController {


    @RequestMapping("/session/online")
    @ResponseBody
    public Response onlineSession(){
        return Response.ok(AfagoalSecurityContextRepository.onlineSession.values());
    }


}
