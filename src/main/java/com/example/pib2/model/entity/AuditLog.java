package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Data
@EqualsAndHashCode(callSuper = true)
public class AuditLog extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private User actor;
    
    @Column(name = "actor_type", length = 50)
    private String actorType; // USER, SYSTEM, API, etc.
    
    @Column(name = "entity_type", nullable = false, length = 100)
    private String entityType;
    
    @Column(name = "entity_id", nullable = false)
    private Long entityId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, length = 50)
    private Action action;
    
    @Column(name = "old_values", columnDefinition = "jsonb")
    private String oldValues;
    
    @Column(name = "new_values", columnDefinition = "jsonb")
    private String newValues;
    
    @Column(name = "diff", columnDefinition = "jsonb")
    private String diff;
    
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    @Column(name = "user_agent", length = 1000)
    private String userAgent;
    
    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;
    
    @Column(name = "event_timestamp", nullable = false)
    private LocalDateTime eventTimestamp = LocalDateTime.now();
    
    public enum Action {
        CREATE,
        READ,
        UPDATE,
        DELETE,
        LOGIN,
        LOGOUT,
        EXPORT,
        IMPORT,
        APPROVE,
        REJECT,
        ASSIGN,
        UNASSIGN
    }
}
