package com.gkfcsolution.springboot_project_structure.app.service;

import com.gkfcsolution.springboot_project_structure.app.model.dto.CreateUserRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.UserDTO;

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
}
