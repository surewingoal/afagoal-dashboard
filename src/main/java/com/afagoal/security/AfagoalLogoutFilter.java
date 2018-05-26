package com.afagoal.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by BaoCai on 18/5/26.
 * Description:
 * 登出时，清除session
 */
public class AfagoalLogoutFilter extends GenericFilterBean {

    private AfagoalSecurityContextRepository securityContextRepository;

    private static final String LOGOUT_PATTERN = "/logout";

    AntPathRequestMatcher logoutMatcher = new AntPathRequestMatcher(LOGOUT_PATTERN);

    public AfagoalLogoutFilter(AfagoalSecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (logoutMatcher.matches(request)) {
            securityContextRepository.invalidSession(request);
        }
        chain.doFilter(request, response);
    }
}
