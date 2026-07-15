package com.gkfcsolution.springboot_project_structure.app.service.impl;

import com.gkfcsolution.springboot_project_structure.app.exception.ResourceNotFoundException;
import com.gkfcsolution.springboot_project_structure.app.model.dto.CreateUserRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.UpdateUserRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.UserDTO;
import com.gkfcsolution.springboot_project_structure.app.model.entity.User;
import com.gkfcsolution.springboot_project_structure.app.model.enums.Role;
import com.gkfcsolution.springboot_project_structure.app.model.mappers.UserMapper;
import com.gkfcsolution.springboot_project_structure.app.repository.UserRepository;
import com.gkfcsolution.springboot_project_structure.app.service.EmailService;
import com.gkfcsolution.springboot_project_structure.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2026 at 12:50
 * File: UserServiceImpl.java.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 12:50
 */

@Service
@RequiredArgsConstructor
@Slf4j  // Lombok génère un logger
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private EmailService emailService;  // Spring injecte la bonne implémentation selon le profil
    private final UserMapper userMapper;

    // Create
    @Transactional  // Cette méthode modifie la base de données
    public UserDTO createUser(CreateUserRequest request) {
        log.info("Creating user with email: {}", request.getEmail());

        // Validation Metier: L'email doit etre unique
        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("Email {} already exists", request.getEmail());
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        User saveUser = userRepository.save(user);

        log.info("User created with ID: {}", saveUser.getId());

        return userMapper.toDTO(saveUser);
    }

    // READ - Récupérer tous les utilisateurs
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }


    // READ - Récupérer un utilisateur par son ID
    public UserDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new ResourceNotFoundException("User not found with ID: " + id);
                });
        return userMapper.toDTO(user);
    }

    // UPDATE
    @Transactional
    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        log.info("Updating user with ID: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new ResourceNotFoundException("User not found with ID: " + id);
                });

        // Validation Metier: L'email doit etre unique
        if (request.getEmail() != null){
            if (!existingUser.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                log.error("Email {} already exists", request.getEmail());
                throw new RuntimeException("Email already exists: " + request.getEmail());
            }
            existingUser.setEmail(request.getEmail());
        }

        if (request.getFirstName() != null) {
            existingUser.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            existingUser.setLastName(request.getLastName());
        }

        User updatedUser = userRepository.save(existingUser);
        log.info("User updated with ID: {}", updatedUser.getId());

        return userMapper.toDTO(updatedUser);
    }

    // DELETE
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new ResourceNotFoundException("User not found with ID: " + id);
                });

        userRepository.delete(existingUser);
        log.info("User deleted with ID: {}", id);
    }

    @Override
    public UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return userMapper.toDTO(user);
    }
}
