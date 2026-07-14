package com.gkfcsolution.springboot_project_structure.app.service;

/**
 * Created on 2026 at 14:39
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 14:39
 */
public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
