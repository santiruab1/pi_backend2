package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "auth_events")
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthEvent extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 50)
    private EventType eventType;
    
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    @Column(name = "user_agent", length = 1000)
    private String userAgent;
    
    @Column(name = "device_info", length = 500)
    private String deviceInfo;
    
    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;
    
    @Column(name = "success", nullable = false)
    private Boolean success;
    
    @Column(name = "failure_reason", length = 255)
    private String failureReason;
    
    @Column(name = "event_timestamp", nullable = false)
    private LocalDateTime eventTimestamp = LocalDateTime.now();
    
    public enum EventType {
        LOGIN_SUCCESS,
        LOGIN_FAILED,
        LOGOUT,
        PASSWORD_CHANGED,
        PASSWORD_RESET_REQUESTED,
        PASSWORD_RESET_COMPLETED,
        EMAIL_VERIFIED,
        MFA_ENABLED,
        MFA_DISABLED,
        MFA_VERIFIED,
        MFA_FAILED,
        ACCOUNT_LOCKED,
        ACCOUNT_UNLOCKED,
        REFRESH_TOKEN_ISSUED,
        REFRESH_TOKEN_REVOKED,
        TOKEN_EXPIRED
    }
}
