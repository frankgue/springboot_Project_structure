package com.gkfcsolution.springboot_project_structure.app.model.dto;

/**
 * Created on 2026 at 13:24
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 13:24
 */
public record Enable2FAResponse(
        String qrCodeDataUri,
        String secret,
        String message
) {
    public Enable2FAResponse(String qrCodeDataUri, String secret) {
        this(qrCodeDataUri, secret, "Scan the QR code with Google Authenticator");
    }
}
