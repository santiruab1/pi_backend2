package com.example.pib2.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncryptionService {
    
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    
    @Value("${app.encryption.key:defaultSecretKey123456789012345678901234567890}")
    private String secretKeyString;
    
    private SecretKey secretKey;
    
    public EncryptionService() {
        initializeSecretKey();
    }
    
    private void initializeSecretKey() {
        try {
            // Si no hay clave configurada, generar una nueva
            if (secretKeyString == null || secretKeyString.equals("defaultSecretKey123456789012345678901234567890")) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
                keyGenerator.init(256); // 256 bits
                secretKey = keyGenerator.generateKey();
                System.out.println("NUEVA CLAVE DE ENCRIPTACIÓN GENERADA: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
                System.out.println("IMPORTANTE: Guarda esta clave en application.properties como app.encryption.key");
            } else {
                // Usar la clave configurada
                byte[] keyBytes = Base64.getDecoder().decode(secretKeyString);
                secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando clave de encriptación", e);
        }
    }
    
    /**
     * Encripta un texto usando AES
     */
    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encriptando texto", e);
        }
    }
    
    /**
     * Desencripta un texto usando AES
     */
    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error desencriptando texto", e);
        }
    }
    
    /**
     * Verifica si un texto está encriptado (base64 válido)
     */
    public boolean isEncrypted(String text) {
        try {
            Base64.getDecoder().decode(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Genera una nueva clave de encriptación
     */
    public String generateNewKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(256);
            SecretKey newKey = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(newKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Error generando nueva clave", e);
        }
    }
}
