package com.example.pib2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Servicio para comunicarse con la API de Siigo
 * Utiliza los access keys encriptados para autenticarse
 */
@Service
public class SiigoApiService {
    
    @Autowired
    private SiigoApiUserService siigoApiUserService;
    
    @Autowired
    private RestTemplate restTemplate;
    
    private static final String SIGIO_API_BASE_URL = "https://api.siigo.com/v1";
    
    /**
     * Ejemplo de cómo obtener datos de Siigo usando un access key encriptado
     */
    public ResponseEntity<String> getSiigoData(String userEmail, String endpoint) {
        // Obtener el access key desencriptado
        Optional<String> accessKeyOpt = siigoApiUserService.getDecryptedAccessKey(userEmail);
        
        if (accessKeyOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario no encontrado o access key inválido");
        }
        
        String accessKey = accessKeyOpt.get();
        
        try {
            // Configurar headers para la API de Siigo
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessKey);
            headers.set("Content-Type", "application/json");
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // Realizar la petición a Siigo
            String url = SIGIO_API_BASE_URL + endpoint;
            ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                entity, 
                String.class
            );
            
            return response;
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error comunicándose con Siigo: " + e.getMessage());
        }
    }
    
    /**
     * Ejemplo de cómo enviar datos a Siigo usando un access key encriptado
     */
    public ResponseEntity<String> postSiigoData(String userEmail, String endpoint, Object data) {
        // Obtener el access key desencriptado
        Optional<String> accessKeyOpt = siigoApiUserService.getDecryptedAccessKey(userEmail);
        
        if (accessKeyOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario no encontrado o access key inválido");
        }
        
        String accessKey = accessKeyOpt.get();
        
        try {
            // Configurar headers para la API de Siigo
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessKey);
            headers.set("Content-Type", "application/json");
            
            HttpEntity<Object> entity = new HttpEntity<>(data, headers);
            
            // Realizar la petición a Siigo
            String url = SIGIO_API_BASE_URL + endpoint;
            ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                entity, 
                String.class
            );
            
            return response;
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error comunicándose con Siigo: " + e.getMessage());
        }
    }
}
