package com.gkfcsolution.springboot_project_structure.app.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.gkfcsolution.springboot_project_structure.app.service.RateLimitService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2026 at 14:37
 * File: RateLimitServiceImpl.java.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 14:37
 */
@Service
public class RateLimitServiceImpl implements RateLimitService {

    private static final int MAX_ATTEMPTS = 5; // Nombre max d'essais autorisés
    private static final int BLOCK_DURATION_MINUTES = 15; // Temps de blocage

    // Cache stockant la clé (IP + userId) et le nombre d'échecs
    private final Cache<String, Integer> attemptsCache;

    public RateLimitServiceImpl() {
        this.attemptsCache = Caffeine.newBuilder()
                .expireAfterWrite(BLOCK_DURATION_MINUTES, TimeUnit.MINUTES)
                .build();
    }

    /**
     * Vérifie si l'utilisateur ou l'IP est bloqué(e).
     */
    @Override
    public boolean isBlocked(String key) {
        Integer attempts = attemptsCache.getIfPresent(key);
        return attempts != null && attempts >= MAX_ATTEMPTS;
    }

    /**
     * Enregistre un échec de validation et incrémente le compteur.
     */
    @Override
    public void recordFailure(String key) {
        Integer attempts = attemptsCache.getIfPresent(key);
        if (attempts == null) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    /**
     * Réinitialise les tentatives de l'utilisateur (après une authentification réussie).
     */
    @Override
    public void reset(String key) {
        attemptsCache.invalidate(key);
    }
}
