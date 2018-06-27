package com.afagoal.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by BaoCai on 18/6/27.
 * Description:
 */
public class AfagoalTokenExtractor implements TokenExtractor {

    private static final String AFAGOAL_TOKEN = "afagoal";

    public static final AfagoalTokenExtractor AfagoalTokenExtractor_INSTANCE = new AfagoalTokenExtractor();

    private AfagoalTokenExtractor() {
    }

    @Override
    public Authentication extract(HttpServletRequest request) {
        String tokenValue = extractToken(request);
        if (tokenValue != null) {
            PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(tokenValue, "");
            return authentication;
        }
        return null;
    }

    protected String extractToken(HttpServletRequest request) {
        String token = extractHeaderToken(request);

        if (token == null) {
            System.out.println("Token not found in headers. Trying request parameters.");
            token = request.getParameter("Authorization");
            if (token == null) {
                System.out.println("Token not found in request parameters.  Not an OAuth2 request.");
            }
        }

        return token;
    }

    protected String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while (headers.hasMoreElements()) { // typically there is only one (most servers enforce that)
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(AFAGOAL_TOKEN.toLowerCase()))) {
                String authHeaderValue = value.substring(AFAGOAL_TOKEN.length()).trim();
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}
