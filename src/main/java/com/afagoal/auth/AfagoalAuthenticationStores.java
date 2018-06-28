package com.afagoal.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * Created by BaoCai on 18/6/27.
 * Description:
 */
public class AfagoalAuthenticationStores implements AuthenticationStores {

    private static final int TOKEN_ALIVE_DAYS = 1;

    private final ConcurrentHashMap<String, TTLAuthentication> onlineAuthentication;

    public AfagoalAuthenticationStores(ConcurrentHashMap<String, TTLAuthentication> onlineAuthentication) {
        this.onlineAuthentication = onlineAuthentication;
    }

    @Override
    public void saveAuthentication(String token, Authentication authentication) {
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("请先生成token！");
        }
        onlineAuthentication.put(token, new TTLAuthentication(authentication));
    }

    @Override
    public void timeout() {
        onlineAuthentication.entrySet().forEach(entry -> {
            if (entry.getValue().getTimeToLeave().isBefore(LocalDateTime.now())) {
                onlineAuthentication.remove(entry.getKey());
            }
        });
    }


    @Override
    public Authentication getAuthentication(String token) {
        TTLAuthentication ttlAuthentication = this.onlineAuthentication.get(token);
        if (null != ttlAuthentication) {
            ttlAuthentication.setTimeToLeave(LocalDateTime.now().plusDays(TOKEN_ALIVE_DAYS));
            return ttlAuthentication.getAuthentication();
        }
        return null;
    }

    @Override
    public Collection<Authentication> onlineAuthentications() {
        return onlineAuthentication.values().stream()
                .map(ttlAuthentication -> ttlAuthentication.getAuthentication())
                .collect(Collectors.toList());
    }

    @Getter
    private static class TTLAuthentication {
        private LocalDateTime timeToLeave;
        private final Authentication authentication;

        public TTLAuthentication(Authentication authentication) {
            this(LocalDateTime.now().plusDays(TOKEN_ALIVE_DAYS), authentication);
        }

        private TTLAuthentication(LocalDateTime timeToLeave, Authentication authentication) {
            this.timeToLeave = timeToLeave;
            this.authentication = authentication;
        }

        private void setTimeToLeave(LocalDateTime timeToLeave) {
            this.timeToLeave = timeToLeave;
        }
    }
}
