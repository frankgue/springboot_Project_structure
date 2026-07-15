package com.gkfcsolution.springboot_project_structure.app.service;

/**
 * Created on 2026 at 13:21
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 13:21
 */
public interface TwoFactorAuthService {
    String generateSecret();
    byte[] generateQrCodeDataUri(String secret, String email);
    boolean verifyCode(String secret, String code);
}
