package com.gkfcsolution.springboot_project_structure.app.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Created on 2026 at 13:25
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 13:25
 */
public record Verify2FARequest(
        @NotBlank String code
) {}