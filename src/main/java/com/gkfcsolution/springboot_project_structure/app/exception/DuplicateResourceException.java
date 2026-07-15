package com.gkfcsolution.springboot_project_structure.app.exception;

/**
 * Created on 2026 at 12:41
 * File: null.java
 * Project: crud_complet_springboot
 *
 * @author Frank GUEKENG
 * @date 03/07/2026
 * @time 12:41
 */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
