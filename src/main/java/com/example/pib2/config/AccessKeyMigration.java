package com.example.pib2.config;

import com.example.pib2.model.entity.SiigoApiUser;
import com.example.pib2.repository.SiigoApiUserRepository;
import com.example.pib2.service.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Script de migración para encriptar access keys existentes
 * Este componente se ejecuta al iniciar la aplicación y migra
 * los access keys que estén en texto plano o hasheados a formato encriptado
 */
@Component
public class AccessKeyMigration implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AccessKeyMigration.class);

    @Autowired
    private SiigoApiUserRepository siigoApiUserRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        logger.info("Iniciando migración de access keys a encriptación AES...");
        
        List<SiigoApiUser> users = siigoApiUserRepository.findAll();
        int migratedCount = 0;
        int skippedCount = 0;
        
        for (SiigoApiUser user : users) {
            String accessKey = user.getAccessKey();
            
            // Verificar si el access key ya está encriptado
            if (!isEncrypted(accessKey)) {
                logger.info("Migrando access key para usuario: {} (ID: {})", user.getEmail(), user.getId());
                
                try {
                    // Encriptar el access key original
                    String encryptedAccessKey = encryptionService.encrypt(accessKey);
                    user.setAccessKey(encryptedAccessKey);
                    
                    siigoApiUserRepository.save(user);
                    migratedCount++;
                    logger.info("Access key migrado exitosamente para usuario: {}", user.getEmail());
                } catch (Exception e) {
                    logger.error("Error migrando access key para usuario {}: {}", user.getEmail(), e.getMessage());
                }
            } else {
                skippedCount++;
                logger.debug("Access key ya encriptado para usuario: {}", user.getEmail());
            }
        }
        
        logger.info("Migración completada. {} access keys migrados, {} ya estaban encriptados.", 
                   migratedCount, skippedCount);
    }
    
    /**
     * Verifica si un string está encriptado con AES
     */
    private boolean isEncrypted(String accessKey) {
        if (accessKey == null || accessKey.length() < 10) {
            return false;
        }
        
        // Verificar si es un hash BCrypt (formato anterior)
        if (accessKey.startsWith("$2") && accessKey.length() == 60) {
            return false; // Necesita migración de BCrypt a AES
        }
        
        // Verificar si es texto encriptado (base64 válido)
        return encryptionService.isEncrypted(accessKey);
    }
}
