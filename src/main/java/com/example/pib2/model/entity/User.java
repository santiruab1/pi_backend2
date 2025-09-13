package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"roles", "refreshTokens", "authEvents", "auditLogs"})
public class User extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_identification_number", nullable = false, length = 50, unique = true)
    private String identificationNumber;

    @Column(name = "user_first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "user_sur_name", nullable = false, length = 100)
    private String userSurName;

    @Column(name = "user_email", nullable = false, length = 200, unique = true)
    private String userEmail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;
    
    @Column(name = "password_algo", nullable = false, length = 20)
    private String passwordAlgo = "bcrypt";

    @Column(name = "user_phone_number", nullable = false, length = 20)
    private String userPhoneNumber;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "is_email_verified", nullable = false)
    private Boolean isEmailVerified = false;
    
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    
    @Column(name = "failed_login_attempts", nullable = false)
    private Integer failedLoginAttempts = 0;
    
    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;
    
    @Column(name = "mfa_secret", length = 32)
    private String mfaSecret;
    
    @Column(name = "mfa_enabled", nullable = false)
    private Boolean mfaEnabled = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RefreshToken> refreshTokens = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AuthEvent> authEvents = new HashSet<>();
    
    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AuditLog> auditLogs = new HashSet<>();

    // @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    // private java.util.List<UserCompany> userCompanies;
}
