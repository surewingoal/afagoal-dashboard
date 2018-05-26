package com.afagoal.security;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by BaoCai on 18/5/26.
 * Description:
 */
public class CookieUtils {

    public static final String DATE_FORMAT_PATTERN_HTTP = "EEE, dd MMM yyyyy HH:mm:ss z";

    /**
     * 获取cookie中对应的值
     *
     * @param request
     *            request请求对象
     * @param name
     *            cookie中的key
     * @return cookie对应值
     */
    public static String getValue(HttpServletRequest request, String name) {
        Cookie cookie = get(request, name);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 通过遍历request中带过来的cookie获取到与key匹配的cookie值
     *
     * @param request
     *            request请求对象
     * @param name
     *            cookie中的key
     * @return
     */
    public static Cookie get(HttpServletRequest request, String name) {
        if (name != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie != null && name.equals(cookie.getName())) {
                        return cookie;
                    }
                }
            }
        }

        return null;
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name
     *            cookie 名称
     * @param value
     *            cookie 值
     */
    public static void add(HttpServletResponse response, String name, String value) {
        add(response, name, value, null, null, false, -1);
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name
     *            cookie 名称
     * @param value
     *            cookie 值
     * @param age
     *            有效时间
     */
    public static void add(HttpServletResponse response, String name, String value, int age) {
        add(response, name, value, null, null, false, age);
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name
     *            cookie 名称
     * @param value
     *            cookie 值
     * @param domain
     *            主域名
     * @param age
     *            有效时间
     */
    public static void add(HttpServletResponse response, String name, String value, String domain, int age) {
        add(response, name, value, domain, null, false, age);
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name
     *            cookie 名称
     * @param value
     *            cookie 值
     * @param domain
     *            主域名
     * @param path
     *            路径
     * @param httpOnly
     *            是否仅http
     * @param age
     *            有效时间
     */
    public static void add(HttpServletResponse response, String name, String value, String domain, String path,
                           boolean httpOnly, int age) {
        if (StringUtils.isNotEmpty(name)) {
            StringBuilder buff = new StringBuilder(128);
            buff.append(name);
            buff.append("=");
            value = StringUtils.isEmpty(value) ? "" : value;
            buff.append(value);
            buff.append(";");

            path = StringUtils.isEmpty(path) ? "/" : path;
            buff.append("Path=");
            buff.append(path);
            buff.append(";");

            if (domain != null) {
                buff.append("Domain=");
                buff.append(domain);
                buff.append(";");
            }

            if (age > 0) {
                buff.append("Expires=");

                Date date = new Date(System.currentTimeMillis() + age * 1000l);
                String ttl =  new SimpleDateFormat(DATE_FORMAT_PATTERN_HTTP, Locale.US).format(new Date(System.currentTimeMillis() + age * 1000l) == null ? new Date()
                        : date);
                buff.append(ttl);

                buff.append(";");
            }else if(age==0){
                buff.append("max-age=0");
                buff.append(";");
            }

            if (httpOnly) {
                buff.append("HTTPOnly");
            }

            response.addHeader("Set-Cookie", buff.toString());
        }
    }
}
