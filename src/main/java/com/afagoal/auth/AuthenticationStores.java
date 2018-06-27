package com.afagoal.auth;

import org.springframework.security.core.Authentication;

import java.util.Collection;

/**
 * Created by BaoCai on 18/6/27.
 * Description:
 */
public interface AuthenticationStores {

    void saveAuthentication(String token, Authentication authentication);

    void removeAuthentication(String token);

    Authentication getAuthentication(String token);

    Collection<Authentication> onlineAuthentications();
}
