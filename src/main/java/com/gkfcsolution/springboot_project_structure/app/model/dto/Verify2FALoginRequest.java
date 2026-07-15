package com.gkfcsolution.springboot_project_structure.app.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Created on 2026 at 13:37
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 13:37
 */
public record Verify2FALoginRequest(
        @NotBlank @Email String email,
        @NotBlank String code
) {}
