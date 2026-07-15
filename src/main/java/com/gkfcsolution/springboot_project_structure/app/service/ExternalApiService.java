package com.gkfcsolution.springboot_project_structure.app.service;

/**
 * Created on 2026 at 15:43
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 15:43
 */
public interface ExternalApiService {
    String callExternalApi();
    String appelerApiInstable();
    String fallbackPourAppelExterne(Throwable t);
}
