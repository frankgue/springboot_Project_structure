package com.gkfcsolution.springboot_project_structure.app.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Created on 2026 at 12:46
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 12:46
 */
@Data
public class CreateUserRequest {
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String name;
}
