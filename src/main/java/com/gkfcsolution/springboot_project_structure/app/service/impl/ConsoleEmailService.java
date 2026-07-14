package com.gkfcsolution.springboot_project_structure.app.service.impl;

import com.gkfcsolution.springboot_project_structure.app.service.EmailService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Created on 2026 at 14:39
 * File: ConsoleEmailService.java.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 14:39
 */
@Service
//@Profile("dev")  // Activé seulement en profil "dev"
//@Profile("!prod")  // Activé partout SAUF en prod
@Profile({"dev", "test"})  // Activé en dev ET en test
public class ConsoleEmailService implements EmailService {
    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("===== EMAIL =====");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("=================");
    }
}
