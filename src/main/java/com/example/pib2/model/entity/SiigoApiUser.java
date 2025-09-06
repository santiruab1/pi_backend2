package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "siigo_api_users")
@Data
public class SiigoApiUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "siigo_api_email", nullable = false, length = 120)
    private String email;

    @Column(name = "siigo_api_access_key", nullable = false)
    private String accessKey;

    @Column(name = "siigo_api_app_type", nullable = false)
    private String appType;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    
    @Column(name = "user_id")
    private Long userId; // Referencia al usuario que hizo la petición
    
    @Column(name = "company_id")
    private Long companyId; // Referencia a la empresa
    
    
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}