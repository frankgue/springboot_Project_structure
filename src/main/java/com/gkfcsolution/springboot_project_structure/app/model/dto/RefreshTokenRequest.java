package com.gkfcsolution.springboot_project_structure.app.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Created on 2026 at 15:53
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 15:53
 */
public record RefreshTokenRequest(
        @NotBlank String refreshToken
) {}
