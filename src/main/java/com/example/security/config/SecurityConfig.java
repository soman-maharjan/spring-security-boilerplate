package com.example.security.config;

import com.example.security.auth.oauth2.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
public class SecurityConfig {

    private final OAuthController oauthController;
    private final CustomAuthorizedClientService customAuthorizedClientService;
    private final CustomAuthorizationRedirectFilter customAuthorizationRedirectFilter;
    private final CustomAuthorizationRequestResolver customAuthorizationRequestResolver;
    private final CustomStatelessAuthorizationRequestRepository customStatelessAuthorizationRequestRepository;

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http
//               .cors(AbstractHttpConfigurer::disable)
               .csrf(AbstractHttpConfigurer::disable)
               .formLogin(AbstractHttpConfigurer::disable)
               .httpBasic(AbstractHttpConfigurer::disable)
                // Endpoint protection
                .authorizeHttpRequests(config -> {
                        config.requestMatchers("/oauth2/**").permitAll();
                        config.anyRequest().authenticated();
                    }
                )
                // Disable "JSESSIONID" cookies
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // OAuth2 (social logins)
                .oauth2Login(config -> {
                    config.authorizationEndpoint(subconfig -> {
                        subconfig.baseUri(OAuthController.AUTHORIZATION_BASE_URL);
                        subconfig.authorizationRequestResolver(this.customAuthorizationRequestResolver);
                        subconfig.authorizationRequestRepository(this.customStatelessAuthorizationRequestRepository);
                    });
                    config.redirectionEndpoint(subconfig -> subconfig.baseUri(OAuthController.CALLBACK_BASE_URL + "/*"));
                    config.authorizedClientService(this.customAuthorizedClientService);
                    config.successHandler(this.oauthController::oauthSuccessResponse);
                    config.failureHandler(this.oauthController::oauthFailureResponse);
                })
                // Filters
                .addFilterBefore(this.customAuthorizationRedirectFilter, OAuth2AuthorizationRequestRedirectFilter.class)
                // Auth exceptions
                .exceptionHandling(config -> {
                    config.accessDeniedHandler(this::unauthorized);
                    config.authenticationEntryPoint(this::unauthorized);
                }).build();
    }

    @SneakyThrows
    private void unauthorized(HttpServletRequest request, HttpServletResponse response, Exception authException) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

}