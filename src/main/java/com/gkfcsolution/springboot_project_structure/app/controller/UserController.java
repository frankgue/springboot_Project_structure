package com.gkfcsolution.springboot_project_structure.app.controller;

import com.gkfcsolution.springboot_project_structure.app.exception.InvalidCodeException;
import com.gkfcsolution.springboot_project_structure.app.exception.TooManyAttemptsException;
import com.gkfcsolution.springboot_project_structure.app.model.dto.*;
import com.gkfcsolution.springboot_project_structure.app.model.entity.User;
import com.gkfcsolution.springboot_project_structure.app.service.TwoFactorAuthService;
import com.gkfcsolution.springboot_project_structure.app.service.UserService;
import com.gkfcsolution.springboot_project_structure.app.service.impl.RateLimitServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 2026 at 12:37
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 14/07/2026
 * @time 12:37
 */

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final TwoFactorAuthService twoFactorAuthService;
    private final RateLimitServiceImpl rateLimitService; // Déclaration à ajouter

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        // Accessible à tous les utilisateurs authentifiés
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        // Admin peut modifier n'importe qui, user peut se modifier lui-même
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @PostMapping("/2fa/enable")
    public ResponseEntity<Enable2FAResponse> enable2FA(@AuthenticationPrincipal User user) {
        log.info("2FA activation requested by user: {}", user.getEmail());
        // Génère un secret unique pour cet utilisateur
        String secret = twoFactorAuthService.generateSecret();

        // Génère le QR Code
        String qrCodeDataUri = Arrays.toString(twoFactorAuthService.generateQrCodeDataUri(secret, user.getEmail()));

        // Stocke le secret (mais n'active pas encore la 2FA)
        userService.set2FASecret(user.getId(), secret);

        return ResponseEntity.ok(new Enable2FAResponse(qrCodeDataUri, secret));
    }

    @PostMapping("/2fa/verify")
    public ResponseEntity<Void> verify2FA(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody Verify2FARequest request,
            HttpServletRequest httpRequest
    ) {
        String ip = getClientIP(httpRequest);

        if (rateLimitService.isBlocked(ip + ":" + user.getId())) {
            throw new TooManyAttemptsException("Too many failed attempts");
        }

        if (!twoFactorAuthService.verifyCode(user.getTwoFactorSecret(), request.code())) {
            rateLimitService.recordFailure(ip + ":" + user.getId());
            throw new InvalidCodeException("Invalid 2FA code");
        }

        rateLimitService.reset(ip + ":" + user.getId());
        userService.enable2FA(user.getId());

        return ResponseEntity.ok().build();
    }


    //Rate limiting sur vérification 2FA
    //Sans rate limiting, un attaquant peut tenter 1 million de combinaisons (000000 → 999999).

   /* @PostMapping("/2fa/verify")
    public ResponseEntity<Void> verify2FA(@AuthenticationPrincipal User user, @Valid @RequestBody Verify2FARequest request) {
        if (!twoFactorAuthService.verifyCode(user.getTwoFactorSecret(), request.code())) {
            log.warn("Invalid 2FA code attempt for user: {}", user.getEmail());
            throw new InvalidCodeException("Invalid 2FA code");
        }

        userService.enable2FA(user.getId());
        log.info("2FA successfully enabled for user: {}", user.getEmail());

        return ResponseEntity.ok().build();
    }*/

    @DeleteMapping("/2fa")
    public ResponseEntity<Void> disable2FA(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody Verify2FARequest request
    ) {
        // Vérifie le code avant de désactiver (sécurité)
        if (!twoFactorAuthService.verifyCode(user.getTwoFactorSecret(), request.code())) {
            throw new InvalidCodeException("Invalid 2FA code");
        }

        userService.disable2FA(user.getId());

        return ResponseEntity.noContent().build();
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty()) {
            return request.getRemoteAddr();
        }
        // X-Forwarded-For peut contenir une liste d'IPs (séparées par des virgules) si la requête passe par plusieurs proxies.
        // La première IP de la liste est celle du client d'origine.
        return xfHeader.split(",")[0].trim();
    }
}
