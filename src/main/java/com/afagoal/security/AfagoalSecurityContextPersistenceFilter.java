package com.afagoal.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.*;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by BaoCai on 18/5/26.
 * Description:
 * 持久化安全上下文
 */
public class AfagoalSecurityContextPersistenceFilter extends GenericFilterBean {

    private SecurityContextRepository securityContextRepository;

    public AfagoalSecurityContextPersistenceFilter(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        createAfagoalCookieIfNecessary(request, response);

        try {
            HttpRequestResponseHolder holder = new HttpRequestResponseHolder(request,
                    response);
            org.springframework.security.core.context.SecurityContext contextBeforeChainExecution
                    = securityContextRepository.loadContext(holder);
            SecurityContextHolder.setContext(contextBeforeChainExecution);
            chain.doFilter(holder.getRequest(), holder.getResponse());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //此方法需要同步
    private synchronized void createAfagoalCookieIfNecessary(HttpServletRequest request, HttpServletResponse response) {
        String afagoalSessionId = CookieUtils.getValue(request, AfagoalSecurityContextRepository.AFAGOAL_SESSION_ID);
        if (StringUtils.isEmpty(afagoalSessionId)) {
            afagoalSessionId = UUID.randomUUID().toString();
            CookieUtils.add(response,
                    AfagoalSecurityContextRepository.AFAGOAL_SESSION_ID,
                    afagoalSessionId);
        }
        AfagoalSecurityContextRepository.COOKIE_HOLDER.set(afagoalSessionId);
    }
}
