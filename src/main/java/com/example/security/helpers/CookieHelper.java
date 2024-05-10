package com.example.security.helpers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;

import java.time.Duration;
import java.util.Optional;

import static java.util.Objects.isNull;

public class CookieHelper {
    private static final String COOKIE_DOMAIN = "localhost";
    private static final Boolean HTTP_ONLY = Boolean.TRUE;
    private static final Boolean SECURE = Boolean.FALSE;

    public static Optional<String> retrieve(Cookie[] cookies, String name){
        if(isNull(cookies)){
            return Optional.empty();
        }
        for (Cookie cookie: cookies){
            if (cookie.getName().equalsIgnoreCase(name)){
                return Optional.ofNullable(cookie.getValue());
            }
        }
        return Optional.empty();
    }

    public static String generateCookie(String name, String value, Duration maxAge, HttpServletRequest request){
        Cookie cookie = new Cookie(name, value);
        if(!"localhost".equals(COOKIE_DOMAIN)){
            cookie.setDomain(COOKIE_DOMAIN);
        }
        cookie.setPath("/");
        cookie.setHttpOnly(HTTP_ONLY);
        cookie.setSecure(SECURE);
        cookie.setMaxAge((int) maxAge.toSeconds());
        Rfc6265CookieProcessor processor = new Rfc6265CookieProcessor();
        return processor.generateHeader(cookie, request);
    }

    public static String generateExpiredCookie(String name, HttpServletRequest request) {
        return generateCookie(name, "-", Duration.ZERO, request);
    }
}
