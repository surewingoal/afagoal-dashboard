package com.afagoal.task.session;

import com.afagoal.auth.AuthenticationStores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by BaoCai on 18/6/28.
 * Description:
 */
@Component
public class SessionManagerTask {

    @Autowired
    private AuthenticationStores authenticationStores;

    @Scheduled(cron = "0 0/10 * * * ? ")
    public void timeout() {
        authenticationStores.timeout();}
}
