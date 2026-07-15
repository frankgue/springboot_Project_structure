package com.gkfcsolution.springboot_project_structure.app.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created on 2026 at 16:29
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 16:29
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        Map<String, Object> error = Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", 403,
                "error", "Forbidden",
                "message", "You don't have permission to access this resource",
                "path", request.getRequestURI()
        );

        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }
}
