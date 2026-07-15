package com.gkfcsolution.springboot_project_structure.app.handler;

import com.gkfcsolution.springboot_project_structure.app.service.JwtService;
import com.gkfcsolution.springboot_project_structure.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created on 2026 at 14:09
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 14:09
 */
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserService userService;

    public OAuth2AuthenticationSuccessHandler(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
/*
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Crée ou récupère l'utilisateur
        User user = userService.findOrCreateOAuth2User(email, name);

        // Génère les tokens JWT
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Redirige vers le frontend avec les tokens
        String targetUrl = String.format(
                "http://localhost:3000/oauth2/redirect?token=%s&refresh=%s",
                accessToken,
                refreshToken
        );

        getRedirectStrategy().sendRedirect(request, response, targetUrl);*/
    }
}
