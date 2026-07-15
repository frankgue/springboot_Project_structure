package com.gkfcsolution.springboot_project_structure.app.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created on 2026 at 16:17
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 16:17
 */
@Service
public class ThirdPartyIntegrationService {

    /**
     * Tente d'exécuter la méthode. En cas d'IOException :
     * - Recommence jusqu'à 3 fois (maxAttempts).
     * - Attend 1000ms au premier échec, puis applique un multiplicateur de 2 (2000ms, puis 4000ms) (backoff).
     */
    @Retryable(
            retryFor = { IOException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public String callExternalService() throws IOException {
        System.out.println("Tentative d'appel au service externe...");

        if (Math.random() > 0.3) {
            throw new IOException("Le serveur distant ne répond pas.");
        }

        return "Connexion réussie !";
    }

    /**
     * Méthode de secours (Recover).
     * Elle est appelée automatiquement si les 3 tentatives échouent.
     * Note : Le premier paramètre doit être l'exception ciblée.
     */
    @Recover
    public String recoverFromFailure(IOException e) {
        System.out.println("Échec final après 3 tentatives. Passage en mode dégradé.");
        return "Données par défaut (service indisponible)";
    }
}
