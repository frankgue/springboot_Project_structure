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
    @NotBlank @Size(min = 8)
    private String password;
    @NotBlank(message = "Le firstName est obligatoire")
    @Size(min = 2, max = 50, message = "Le firstName doit contenir entre 2 et 50 caractères")
    private String firstName;
    @NotBlank(message = "Le lastName est obligatoire")
    @Size(min = 2, max = 50, message = "Le lastName doit contenir entre 2 et 50 caractères")
    private String lastName;
}
