package com.gkfcsolution.springboot_project_structure.app.controller;

import com.gkfcsolution.springboot_project_structure.app.model.dto.AuthenticationResponse;
import com.gkfcsolution.springboot_project_structure.app.model.dto.LoginRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.RefreshTokenRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.RegisterRequest;
import com.gkfcsolution.springboot_project_structure.app.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2026 at 15:53
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 15:53
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
