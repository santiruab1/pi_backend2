package com.example.pib2.controller;

import com.example.pib2.service.SiigoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de ejemplo para demostrar el uso de access keys encriptados
 * con la API de Siigo
 */
@RestController
@RequestMapping("/api/siigo")
public class SiigoApiController {
    
    @Autowired
    private SiigoApiService siigoApiService;
    
    /**
     * Ejemplo: Obtener información de la empresa desde Siigo
     */
    @GetMapping("/company/{userEmail}")
    public ResponseEntity<String> getCompanyInfo(@PathVariable String userEmail) {
        return siigoApiService.getSiigoData(userEmail, "/company");
    }
    
    /**
     * Ejemplo: Obtener productos desde Siigo
     */
    @GetMapping("/products/{userEmail}")
    public ResponseEntity<String> getProducts(@PathVariable String userEmail) {
        return siigoApiService.getSiigoData(userEmail, "/products");
    }
    
    /**
     * Ejemplo: Obtener clientes desde Siigo
     */
    @GetMapping("/customers/{userEmail}")
    public ResponseEntity<String> getCustomers(@PathVariable String userEmail) {
        return siigoApiService.getSiigoData(userEmail, "/customers");
    }
    
    /**
     * Ejemplo: Crear un nuevo producto en Siigo
     */
    @PostMapping("/products/{userEmail}")
    public ResponseEntity<String> createProduct(
            @PathVariable String userEmail,
            @RequestBody Object productData) {
        return siigoApiService.postSiigoData(userEmail, "/products", productData);
    }
}
