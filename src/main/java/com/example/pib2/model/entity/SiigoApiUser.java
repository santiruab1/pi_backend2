package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "siigo_api_users")
@Data
@EqualsAndHashCode(callSuper = true)
public class SiigoApiUser extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "siigo_api_email", nullable = false, length = 120)
    private String email;

    @Column(name = "siigo_api_access_key", nullable = false)
    private String accessKey;

    @Column(name = "siigo_api_app_type", nullable = false)
    private String appType;
    
    @Column(name = "user_id")
    private Long userId; // Referencia al usuario que hizo la petición
    
    @Column(name = "company_id")
    private Long companyId; // Referencia a la empresa
}