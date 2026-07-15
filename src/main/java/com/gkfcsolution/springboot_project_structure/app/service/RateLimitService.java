package com.gkfcsolution.springboot_project_structure.app.service;

/**
 * Created on 2026 at 14:36
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 14:36
 */
public interface RateLimitService {
    boolean isBlocked(String key);
    void recordFailure(String key);
    public void reset(String key);
}
