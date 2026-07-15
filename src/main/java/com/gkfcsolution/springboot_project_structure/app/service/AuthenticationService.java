package com.gkfcsolution.springboot_project_structure.app.service;

import com.gkfcsolution.springboot_project_structure.app.model.dto.AuthenticationResponse;
import com.gkfcsolution.springboot_project_structure.app.model.dto.LoginRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.RefreshTokenRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.RegisterRequest;

/**
 * Created on 2026 at 15:57
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 15:57
 */
public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(LoginRequest request);
    AuthenticationResponse refreshToken(RefreshTokenRequest request);
    AuthenticationResponse verify2FAAndLogin(String email, String code);

}
