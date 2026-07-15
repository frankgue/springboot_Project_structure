package com.gkfcsolution.springboot_project_structure.app.service.impl;

import com.gkfcsolution.springboot_project_structure.app.exception.DuplicateResourceException;
import com.gkfcsolution.springboot_project_structure.app.exception.InvalidTokenException;
import com.gkfcsolution.springboot_project_structure.app.model.dto.AuthenticationResponse;
import com.gkfcsolution.springboot_project_structure.app.model.dto.LoginRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.RefreshTokenRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.RegisterRequest;
import com.gkfcsolution.springboot_project_structure.app.model.entity.User;
import com.gkfcsolution.springboot_project_structure.app.model.enums.Role;
import com.gkfcsolution.springboot_project_structure.app.repository.UserRepository;
import com.gkfcsolution.springboot_project_structure.app.service.AuthenticationService;
import com.gkfcsolution.springboot_project_structure.app.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created on 2026 at 16:19
 * File: AuthenticationServiceImpl.java.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 16:19
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        var user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setRole(Role.USER);
        user.setEnabled(true);

        userRepository.save(user);

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        final String userEmail = jwtService.extractUsername(request.refreshToken());

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!jwtService.isTokenValid(request.refreshToken(), user)) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        var accessToken = jwtService.generateToken(user);
        var newRefreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(accessToken, newRefreshToken);
    }
}
