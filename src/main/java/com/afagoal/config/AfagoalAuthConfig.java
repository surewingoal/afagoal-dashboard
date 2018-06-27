package com.afagoal.config;

import com.afagoal.auth.AfagoalAuthenticationStores;
import com.afagoal.auth.AuthenticationStores;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by BaoCai on 18/6/27.
 * Description:
 */
@Configuration
public class AfagoalAuthConfig {

    @Bean
    public AuthenticationStores authenticationStores() {
        return new AfagoalAuthenticationStores(new ConcurrentHashMap());
    }

}
