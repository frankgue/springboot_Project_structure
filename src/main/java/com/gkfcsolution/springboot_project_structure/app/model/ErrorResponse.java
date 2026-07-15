package com.gkfcsolution.springboot_project_structure.app.model;

import java.time.LocalDateTime;

/**
 * Created on 2026 at 14:32
 * File: null.java
 * Project: crud_complet_springboot
 *
 * @author Frank GUEKENG
 * @date 03/07/2026
 * @time 14:32
 */
public record ErrorResponse(int status, String message, LocalDateTime timestamp) {}
