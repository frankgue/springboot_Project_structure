package com.gkfcsolution.springboot_project_structure.app.exception;

import dev.samstevens.totp.exceptions.QrGenerationException;

/**
 * Created on 2026 at 12:41
 * File: null.java
 * Project: crud_complet_springboot
 *
 * @author Frank GUEKENG
 * @date 03/07/2026
 * @time 12:41
 */
public class TwoFactorAuthException extends RuntimeException {
    public TwoFactorAuthException(String message) {
        super(message);
    }
    public TwoFactorAuthException(String message, QrGenerationException e) {
        super(message,e);
    }

    public TwoFactorAuthException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
