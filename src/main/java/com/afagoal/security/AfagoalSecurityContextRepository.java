package com.afagoal.security;

import com.afagoal.utils.json.CustomDateTimeSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by BaoCai on 18/5/25.
 * Description:
 */
public class AfagoalSecurityContextRepository implements SecurityContextRepository {

    public static final String AFAGOAL_SESSION_ID = "AFAGOAL_SESSION_ID";

    public static ThreadLocal<String> COOKIE_HOLDER = new ThreadLocal();

    private static final int SESSION_ALIVE = 2;

    public static ConcurrentHashMap<String, AfagoalSecurityContext> onlineSession
            = new ConcurrentHashMap();

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        String afagoalSessionId = getAfagoalSessionId(requestResponseHolder.getRequest().getCookies());
        if (StringUtils.isEmpty(afagoalSessionId) || null == onlineSession.get(afagoalSessionId)) {
            return SecurityContextHolder.createEmptyContext();
        }
        return onlineSession.get(afagoalSessionId).getSecurityContext();
    }

    private String getAfagoalSessionId(Cookie[] cookies) {
        if (cookies == null || cookies.length <= 0) {
            return null;
        }

        Cookie afagoalSessionCookie = Arrays.stream(cookies)
                .filter(cookie -> AFAGOAL_SESSION_ID.equals(cookie.getName()))
                .findFirst()
                .orElse(null);
        return afagoalSessionCookie == null ? null : afagoalSessionCookie.getValue();
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
    }

    private boolean valid(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return Boolean.FALSE;
        }
        AfagoalSecurityContext afagoalSecurityContext = onlineSession.get(sessionId);
        return null != afagoalSecurityContext &&
                afagoalSecurityContext.getTimeToLeave().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String afagoalSessionId = CookieUtils.getValue(request, AFAGOAL_SESSION_ID);
        return valid(afagoalSessionId);
    }

    private static class AfagoalSecurityContext {
        private SecurityContext securityContext;
        @JsonSerialize(using = CustomDateTimeSerialize.class)
        private LocalDateTime timeToLeave;

        public AfagoalSecurityContext(SecurityContext securityContext, LocalDateTime timeToLeave) {
            this.securityContext = securityContext;
            this.timeToLeave = timeToLeave;
        }

        public void setSecurityContext(SecurityContext securityContext) {
            this.securityContext = securityContext;
        }

        public void setTimeToLeave(LocalDateTime timeToLeave) {
            this.timeToLeave = timeToLeave;
        }

        public SecurityContext getSecurityContext() {
            return securityContext;
        }

        public LocalDateTime getTimeToLeave() {
            return timeToLeave;
        }
    }


    public void invalidSession(HttpServletRequest request) {
        String sessionId = CookieUtils.getValue(request, AFAGOAL_SESSION_ID);
        if (!StringUtils.isEmpty(sessionId)) {
            onlineSession.remove(sessionId);
        }
    }

    public static void saveContext(SecurityContext securityContext) {
        String cookieValue = COOKIE_HOLDER.get();
        AfagoalUser user = (AfagoalUser) securityContext.getAuthentication().getPrincipal();
        user.setInfo("from afagoal session");
        onlineSession.put(cookieValue
                , new AfagoalSecurityContext(securityContext, LocalDateTime.now().plusHours(SESSION_ALIVE)));
    }
}
