package com.afagoal.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by BaoCai on 18/6/27.
 * Description:
 */
public class AfagoalTokenAuthenticationFilter implements Filter {

    private TokenExtractor tokenExtractor;

    private AuthenticationStores authenticationStores;


    public AfagoalTokenAuthenticationFilter(TokenExtractor tokenExtractor, AuthenticationStores authenticationStores) {
        this.tokenExtractor = tokenExtractor;
        this.authenticationStores = authenticationStores;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            final HttpServletRequest request = (HttpServletRequest) req;
            Authentication tokenValue = tokenExtractor.extract(request);

            if (null == tokenValue) {
                System.out.println("there has no token !");
            } else {
                authentication = authenticationStores.getAuthentication((String) tokenValue.getPrincipal());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
