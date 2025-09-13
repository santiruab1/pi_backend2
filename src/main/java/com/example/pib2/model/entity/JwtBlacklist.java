package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "jwt_blacklist")
@Data
@EqualsAndHashCode(callSuper = true)
public class JwtBlacklist extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "jti", nullable = false, unique = true, length = 255)
    private String jti; // JWT ID
    
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
    
    @Column(name = "revoked_at", nullable = false)
    private LocalDateTime revokedAt = LocalDateTime.now();
    
    @Column(name = "reason", length = 255)
    private String reason; // LOGOUT, SECURITY, ADMIN_REVOKE, etc.
    
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
}
