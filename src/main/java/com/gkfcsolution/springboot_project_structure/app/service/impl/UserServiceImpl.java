package com.gkfcsolution.springboot_project_structure.app.service.impl;

import com.gkfcsolution.springboot_project_structure.app.model.dto.CreateUserRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.UserDTO;
import com.gkfcsolution.springboot_project_structure.app.model.entity.User;
import com.gkfcsolution.springboot_project_structure.app.repository.UserRepository;
import com.gkfcsolution.springboot_project_structure.app.service.EmailService;
import com.gkfcsolution.springboot_project_structure.app.service.UserService;
import lombok.RequiredArgsConstructor;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private EmailService emailService;  // Spring injecte la bonne implémentation selon le profil
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getEmail(), user.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDTO createUser(CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        User savedUser = userRepository.save(user);

        emailService.sendEmail(user.getEmail(), "Bienvenue !", "Merci de vous être inscrit.");

        return new UserDTO(savedUser.getId(), savedUser.getEmail(), savedUser.getName());
    }
}
