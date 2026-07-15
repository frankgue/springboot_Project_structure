package com.gkfcsolution.springboot_project_structure.app.controller;

import com.gkfcsolution.springboot_project_structure.app.model.dto.CreateUserRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.UpdateUserRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.UserDTO;
import com.gkfcsolution.springboot_project_structure.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2026 at 12:37
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 12:37
 */

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        // Accessible à tous les utilisateurs authentifiés
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        // Admin peut modifier n'importe qui, user peut se modifier lui-même
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

}
