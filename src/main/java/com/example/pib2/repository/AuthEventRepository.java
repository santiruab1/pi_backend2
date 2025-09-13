package com.example.pib2.repository;

import com.example.pib2.model.entity.AuthEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuthEventRepository extends JpaRepository<AuthEvent, Long> {
    // Búsquedas por usuario
    List<AuthEvent> findByUserId(Long userId);
    Page<AuthEvent> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    // Búsquedas por tipo de evento
    List<AuthEvent> findByEventType(String eventType);
    List<AuthEvent> findByUserIdAndEventType(Long userId, String eventType);
    
    // Búsquedas por resultado
    List<AuthEvent> findBySuccess(boolean success);
    List<AuthEvent> findByUserIdAndSuccess(Long userId, boolean success);
    
    // Búsquedas por IP
    List<AuthEvent> findByIpAddress(String ipAddress);
    List<AuthEvent> findByIpAddressAndSuccess(String ipAddress, boolean success);
    
    // Búsquedas por fecha
    List<AuthEvent> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    Page<AuthEvent> findByCreatedAtBetweenOrderByCreatedAtDesc(
        LocalDateTime start, 
        LocalDateTime end, 
        Pageable pageable
    );
    
    // Búsquedas combinadas
    List<AuthEvent> findByUserIdAndCreatedAtBetween(
        Long userId, 
        LocalDateTime start, 
        LocalDateTime end
    );
    
    Page<AuthEvent> findByUserIdAndEventTypeAndCreatedAtBetween(
        Long userId,
        String eventType,
        LocalDateTime start,
        LocalDateTime end,
        Pageable pageable
    );
    
    // Búsquedas para seguridad
    long countByUserIdAndSuccessIsFalseAndCreatedAtBetween(
        Long userId,
        LocalDateTime start,
        LocalDateTime end
    );
    
    long countByIpAddressAndSuccessIsFalseAndCreatedAtBetween(
        String ipAddress,
        LocalDateTime start,
        LocalDateTime end
    );
}
