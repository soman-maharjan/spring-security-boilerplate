package com.example.security.helpers;

import jakarta.servlet.http.Cookie;

import java.time.Duration;
import java.util.Optional;

import static java.util.Objects.isNull;

public class CookieHelper {
    private static final String COOKIE_DOMAIN = "localhost";
    private static final Boolean HTTP_ONLY = Boolean.TRUE;
    private static final Boolean SECURE = Boolean.FALSE;
    private static final String SAME_SITE = "Lax";

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

    public static Cookie generateCookie(String name, String value, Duration maxAge){
        Cookie cookie = new Cookie(name, value);
        if(!"localhost".equals(COOKIE_DOMAIN)){
            cookie.setDomain(COOKIE_DOMAIN);
        }
        cookie.setPath("/");
        cookie.setHttpOnly(HTTP_ONLY);
        cookie.setSecure(SECURE);
        cookie.setAttribute("SameSite", SAME_SITE);
        cookie.setMaxAge((int) maxAge.toSeconds());
        return cookie;
    }

    public static Cookie generateExpiredCookie(String name) {
        return generateCookie(name, "-", Duration.ZERO);
    }
}
