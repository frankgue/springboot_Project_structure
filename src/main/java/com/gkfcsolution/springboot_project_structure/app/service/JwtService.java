package com.gkfcsolution.springboot_project_structure.app.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

/**
 * Created on 2026 at 12:25
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 12:25
 */
public interface JwtService {
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);

}
