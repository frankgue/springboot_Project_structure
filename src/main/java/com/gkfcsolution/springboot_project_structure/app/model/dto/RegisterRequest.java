package com.gkfcsolution.springboot_project_structure.app.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Created on 2026 at 15:53
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 15:53
 */
public record RegisterRequest(

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Format d'email invalide")
        String email,
        @NotBlank @Size(min = 8) String password,
        @NotBlank(message = "Le firstName est obligatoire")
        @Size(min = 2, max = 50, message = "Le firstName doit contenir entre 2 et 50 caractères")
        String firstName,
        @NotBlank(message = "Le lastName est obligatoire")
        @Size(min = 2, max = 50, message = "Le lastName doit contenir entre 2 et 50 caractères")
        String lastName
) {}
