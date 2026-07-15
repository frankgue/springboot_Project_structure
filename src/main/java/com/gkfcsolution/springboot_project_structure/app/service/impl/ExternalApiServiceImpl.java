package com.gkfcsolution.springboot_project_structure.app.service.impl;

import com.gkfcsolution.springboot_project_structure.app.service.ExternalApiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

/**
 * Created on 2026 at 15:43
 * File: ExternalApiServiceImpl.java.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 15:43
 */
@Service
public class ExternalApiServiceImpl implements ExternalApiService {
    @Retryable(
            value = { RestClientException.class }
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 1000)
    )
    @CircuitBreaker(name = "externalApi", fallbackMethod = "fallbackPourAppelExterne")
    @Override
    public String callExternalApi() {
        // Retry automatique en cas d'échec
//        return restTemplate.getForObject("https://api.example.com/data", String.class);
        return "External API response";
    }
    // Si cette méthode échoue trop souvent, le circuit s'ouvre et appelle directement la méthode de repli
    @CircuitBreaker(name = "monServiceExterne", fallbackMethod = "fallbackPourAppelExterne")
    @Override
    public String appelerApiInstable() {
        // Logique d'appel HTTP (ex: RestTemplate, WebClient, Feign, etc.)
        if (Math.random() > 0.5) {
            throw new RuntimeException("L'API distante est en panne !");
        }
        return "Données reçues avec succès !";
    }

    // Cette méthode de secours DOIT avoir la même signature que la méthode d'origine
    // plus un argument supplémentaire pour capturer l'exception
    @Override
    public String fallbackPourAppelExterne(Throwable t) {
        return "Système dégradé : Mode hors-ligne activé (Raison : " + t.getMessage() + ")";
    }



}
