package com.example.pib2.repository;

import com.example.pib2.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    // Búsquedas básicas de token
    Optional<RefreshToken> findByToken(String token);
    boolean existsByToken(String token);
    
    // Búsquedas por usuario
    List<RefreshToken> findByUserId(Long userId);
    List<RefreshToken> findByUserIdAndExpirationAfter(Long userId, LocalDateTime now);
    Optional<RefreshToken> findTopByUserIdOrderByCreatedAtDesc(Long userId);
    
    // Búsquedas por dispositivo
    List<RefreshToken> findByUserIdAndDeviceInfo(Long userId, String deviceInfo);
    
    // Validaciones de estado
    boolean existsByUserIdAndExpirationAfter(Long userId, LocalDateTime now);
    
    // Operaciones de limpieza
    @Modifying
    @Transactional
    void deleteByUserId(Long userId);
    
    @Modifying
    @Transactional
    void deleteByExpirationBefore(LocalDateTime date);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.userId = :userId AND r.deviceInfo = :deviceInfo")
    void deleteByUserIdAndDeviceInfo(Long userId, String deviceInfo);
    
    // Conteos y estadísticas
    long countByUserIdAndExpirationAfter(Long userId, LocalDateTime now);
    
    @Query("SELECT COUNT(r) FROM RefreshToken r WHERE r.userId = :userId AND r.expiration > :now AND r.revoked = false")
    long countActiveTokensByUser(Long userId, LocalDateTime now);
    
    // Búsquedas por estado
    List<RefreshToken> findByRevokedTrue();
    List<RefreshToken> findByRevokedFalseAndExpirationAfter(LocalDateTime now);
    
    // Búsquedas combinadas
    @Query("SELECT r FROM RefreshToken r WHERE r.userId = :userId AND r.expiration > :now AND r.revoked = false ORDER BY r.createdAt DESC")
    List<RefreshToken> findActiveTokensByUser(Long userId, LocalDateTime now);
    
    // Invalidación de tokens
    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken r SET r.revoked = true, r.revokedAt = :now WHERE r.userId = :userId AND r.revoked = false")
    void revokeAllUserTokens(Long userId, LocalDateTime now);
    
    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken r SET r.revoked = true, r.revokedAt = :now WHERE r.token = :token")
    void revokeToken(String token, LocalDateTime now);
}
