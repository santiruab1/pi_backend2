package com.example.pib2.repository;

import com.example.pib2.model.entity.JwtBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Long> {
    // Búsquedas básicas
    boolean existsByToken(String token);
    Optional<JwtBlacklist> findByToken(String token);
    
    // Búsquedas por fecha de expiración
    List<JwtBlacklist> findByExpirationBefore(LocalDateTime date);
    List<JwtBlacklist> findByExpirationAfter(LocalDateTime date);
    
    // Búsquedas por motivo de revocación
    List<JwtBlacklist> findByRevocationReason(String reason);
    
    // Búsquedas por usuario
    List<JwtBlacklist> findByUserId(Long userId);
    List<JwtBlacklist> findByUserIdAndExpirationAfter(Long userId, LocalDateTime date);
    
    // Operaciones de limpieza
    @Modifying
    @Transactional
    @Query("DELETE FROM JwtBlacklist j WHERE j.expiration < :date")
    void deleteExpiredTokens(LocalDateTime date);
    
    @Modifying
    @Transactional
    void deleteByExpirationBefore(LocalDateTime date);
    
    // Conteos y validaciones
    long countByUserIdAndExpirationAfter(Long userId, LocalDateTime date);
    
    @Query("SELECT COUNT(j) > 0 FROM JwtBlacklist j WHERE j.token = :token AND j.expiration > :now")
    boolean isTokenBlacklisted(String token, LocalDateTime now);
    
    // Búsquedas combinadas
    List<JwtBlacklist> findByUserIdAndRevocationReason(Long userId, String reason);
    
    @Query("SELECT j FROM JwtBlacklist j WHERE j.userId = :userId AND j.expiration > :now ORDER BY j.createdAt DESC")
    List<JwtBlacklist> findActiveBlacklistedTokensByUser(Long userId, LocalDateTime now);
}
