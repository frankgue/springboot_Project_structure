package com.gkfcsolution.springboot_project_structure.app.service;

import com.gkfcsolution.springboot_project_structure.app.model.dto.CreateUserRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.UpdateUserRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.UserDTO;
import com.gkfcsolution.springboot_project_structure.app.model.entity.User;

import java.util.List;

/**
 * Created on 2026 at 12:33
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 12:33
 */
public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO createUser(CreateUserRequest request);
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
    UserDTO getCurrentUser();

    // 2FA related methods
    // Two-Factor Authentication (2FA)
    void set2FASecret(Long userId, String secret);
    void enable2FA(Long userId);
    void disable2FA(Long userId);

    User findOrCreateOAuth2User(String email, String name);
}
