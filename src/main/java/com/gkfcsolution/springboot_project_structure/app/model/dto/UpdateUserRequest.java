package com.gkfcsolution.springboot_project_structure.app.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2026 at 12:41
 * File: null.java
 * Project: crud_complet_springboot
 *
 * @author Frank GUEKENG
 * @date 03/07/2026
 * @time 12:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

  /*  @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;*/
    @NotBlank(message = "Le firstName est obligatoire")
    @Size(min = 2, max = 50, message = "Le firstName doit contenir entre 2 et 50 caractères")
    private String firstName;
    @NotBlank(message = "Le lastName est obligatoire")
    @Size(min = 2, max = 50, message = "Le lastName doit contenir entre 2 et 50 caractères")
    private String lastName;
}
