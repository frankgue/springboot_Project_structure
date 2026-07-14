package com.gkfcsolution.springboot_project_structure.app.repository;

import com.gkfcsolution.springboot_project_structure.app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 2026 at 12:32
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 12:32
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
