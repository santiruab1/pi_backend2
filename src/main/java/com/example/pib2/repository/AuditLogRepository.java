package com.example.pib2.repository;

import com.example.pib2.model.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    // Búsquedas por entidad
    List<AuditLog> findByEntityTypeAndEntityId(String entityType, Long entityId);
    Page<AuditLog> findByEntityType(String entityType, Pageable pageable);
    
    // Búsquedas por usuario
    List<AuditLog> findByUserId(Long userId);
    List<AuditLog> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);
    
    // Búsquedas por acción
    List<AuditLog> findByAction(String action);
    List<AuditLog> findByActionAndEntityType(String action, String entityType);
    
    // Búsquedas por fecha
    List<AuditLog> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    // Búsquedas combinadas
    Page<AuditLog> findByEntityTypeAndActionAndCreatedAtBetween(
        String entityType, 
        String action, 
        LocalDateTime start, 
        LocalDateTime end, 
        Pageable pageable
    );
    
    // Búsquedas por estado
    List<AuditLog> findByStatus(String status);
    
    // Búsquedas con paginación
    Page<AuditLog> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    Page<AuditLog> findByEntityTypeOrderByCreatedAtDesc(String entityType, Pageable pageable);
}
