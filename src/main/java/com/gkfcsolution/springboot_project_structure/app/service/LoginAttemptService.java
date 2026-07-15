package com.gkfcsolution.springboot_project_structure.app.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2026 at 14:13
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 14:13
 */
@Component
public class LoginAttemptService {

    private final LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        // Cache qui stocke le nombre de tentatives par IP
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES) // Supprime automatiquement après 15 min
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) {
                        return 0; // Valeur par défaut : 0 tentative
                    }
                });
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key); // Supprime l'IP du cache (réinitialise le compteur)
    }

    public void loginFailed(String key) {
        int attempts = attemptsCache.getUnchecked(key); // Récupère le nombre actuel
        attemptsCache.put(key, attempts + 1); // Incrémente de 1
    }

    public boolean isBlocked(String key) {
        return attemptsCache.getUnchecked(key) >= 5; // Bloqué si >= 5 tentatives
    }
}
