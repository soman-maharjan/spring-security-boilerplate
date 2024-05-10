package com.example.security.auth.oauth2;

import com.example.security.auth.AuthenticationHelper;
import com.example.security.helpers.CookieHelper;
import com.example.security.user.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Duration;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class OAuthController {

    public static final String AUTHORIZATION_BASE_URL = "/oauth2/authorization";

    public static final String CALLBACK_BASE_URL = "/oauth2/callback";

    public static final String OAUTH_COOKIE_NAME = "OAUTH";

    public static final String SESSION_COOKIE_NAME = "SESSION";

    private final AccountService accountService;

    @SneakyThrows
    public void oauthRedirectResponse(HttpServletRequest request, HttpServletResponse response, String url) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{ \"redirectUrl\": \"%s\" }".formatted(url));
    }

    @SneakyThrows
    public void oauthSuccessCallback(OAuth2AuthorizedClient client, Authentication authentication) {
        // You can grab the access + refresh tokens as well via the "client"
        UUID accountId = this.accountService.findOrRegisterAccount(
                authentication.getName(),
                authentication.getName().split("\\|")[0],
                ((DefaultOidcUser) authentication.getPrincipal()).getClaims()
        );
        AuthenticationHelper.attachAccountId(authentication, accountId.toString());
    }

    @SneakyThrows
    public void oauthSuccessResponse(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accountId = AuthenticationHelper.retrieveAccountId(authentication);
        response.addHeader(HttpHeaders.SET_COOKIE, CookieHelper.generateExpiredCookie(OAUTH_COOKIE_NAME, request));
        response.addHeader(HttpHeaders.SET_COOKIE, CookieHelper.generateCookie(SESSION_COOKIE_NAME, accountId, Duration.ofDays(1), request));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{ \"status\": \"success\" }");
    }

    @SneakyThrows
    public void oauthFailureResponse(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(HttpHeaders.SET_COOKIE, CookieHelper.generateExpiredCookie(OAUTH_COOKIE_NAME, request));
        response.getWriter().write("{ \"status\": \"failure\" }");
    }

}