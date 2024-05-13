package com.example.security.auth.oauth2;

import com.example.security.auth.AuthenticationHelper;
import com.example.security.auth.oauth2.user.Oauth2User;
import com.example.security.helpers.CookieHelper;
import com.example.security.jwt.JwtService;
import com.example.security.user.enduser.EndUser;
import com.example.security.user.enduser.EndUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;

import java.time.Duration;

@Controller
@RequiredArgsConstructor
public class OAuthController {

    public static final String AUTHORIZATION_BASE_URL = "/oauth2/authorization";

    public static final String CALLBACK_BASE_URL = "/oauth2/callback";

    public static final String OAUTH_COOKIE_NAME = "OAUTH";

    public static final String TOKEN_COOKIE_NAME = "TOKEN";

    private final EndUserService endUserService;

    private final JwtService jwtService;

    @SneakyThrows
    public void oauthRedirectResponse(HttpServletRequest request, HttpServletResponse response, String url) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{ \"redirectUrl\": \"%s\" }".formatted(url));
    }

    @SneakyThrows
    public void oauthSuccessCallback(OAuth2AuthorizedClient client, Authentication authentication) {
        Oauth2User oauth2user = new Oauth2User(((DefaultOidcUser) authentication.getPrincipal()).getClaims());

        String email = this.endUserService.findOrRegisterUser(oauth2user);
        AuthenticationHelper.attachEmail(authentication, email);
    }

    @SneakyThrows
    public void oauthSuccessResponse(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String email = AuthenticationHelper.retrieveEmail(authentication);
        EndUser user = endUserService.findByEmail(email);

        String token = jwtService.generateToken(user);

        response.addCookie(CookieHelper.generateExpiredCookie(OAUTH_COOKIE_NAME));
        response.addCookie(CookieHelper.generateCookie(TOKEN_COOKIE_NAME, token, Duration.ofDays(1)));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @SneakyThrows
    public void oauthFailureResponse(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addCookie(CookieHelper.generateExpiredCookie(OAUTH_COOKIE_NAME));
        response.getWriter().write("{ \"status\": \"failure\" }");
    }

}