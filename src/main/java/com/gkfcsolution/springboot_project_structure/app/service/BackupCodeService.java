package com.gkfcsolution.springboot_project_structure.app.service;

import java.util.List;

/**
 * Created on 2026 at 13:38
 * File: null.java
 * Project: springboot_Project_structure
 *
 * @author Frank GUEKENG
 * @date 15/07/2026
 * @time 13:38
 */
public interface BackupCodeService {
    List<String> generateBackupCodes(int count);
}
