package com.gkfcsolution.springboot_project_structure.app.model.mappers;

import com.gkfcsolution.springboot_project_structure.app.model.dto.CreateUserRequest;
import com.gkfcsolution.springboot_project_structure.app.model.dto.UserDTO;
import com.gkfcsolution.springboot_project_structure.app.model.entity.User;
import org.springframework.stereotype.Component;

/**
 * Created on 2026 at 12:41
 * File: null.java
 * Project: crud_complet_springboot
 *
 * @author Frank GUEKENG
 * @date 03/07/2026
 * @time 12:41
 */
@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }

    public User toEntity(CreateUserRequest request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
        return user;
    }

}
