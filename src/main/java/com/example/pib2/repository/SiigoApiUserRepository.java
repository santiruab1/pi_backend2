package com.example.pib2.repository;

import com.example.pib2.model.entity.SiigoApiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SiigoApiUserRepository extends JpaRepository<SiigoApiUser, Long> {
    
    // Buscar por email
    Optional<SiigoApiUser> findByEmail(String email);
    
    // Buscar por usuario
    List<SiigoApiUser> findByUserId(Long userId);
    
    // Buscar por empresa
    List<SiigoApiUser> findByCompanyId(Long companyId);
    
    // Buscar por tipo de aplicación
    List<SiigoApiUser> findByAppType(String appType);
    
    // Buscar por usuario y empresa
    List<SiigoApiUser> findByUserIdAndCompanyId(Long userId, Long companyId);
    
    // Verificar si existe un email para un usuario específico
    boolean existsByEmailAndUserId(String email, Long userId);
    
    // Verificar si existe un email para una empresa específica
    boolean existsByEmailAndCompanyId(String email, Long companyId);
    
    // Buscar por rango de fechas
    List<SiigoApiUser> findByCreatedAtBetween(Date startDate, Date endDate);
    
    // Buscar usuarios recientes por usuario
    @Query("SELECT s FROM SiigoApiUser s WHERE s.userId = :userId AND s.createdAt >= :startDate ORDER BY s.createdAt DESC")
    List<SiigoApiUser> findRecentByUser(@Param("userId") Long userId, @Param("startDate") Date startDate);
    
    // Buscar usuarios recientes por empresa
    @Query("SELECT s FROM SiigoApiUser s WHERE s.companyId = :companyId AND s.createdAt >= :startDate ORDER BY s.createdAt DESC")
    List<SiigoApiUser> findRecentByCompany(@Param("companyId") Long companyId, @Param("startDate") Date startDate);
    
    // Contar usuarios por empresa
    @Query("SELECT COUNT(s) FROM SiigoApiUser s WHERE s.companyId = :companyId")
    Long countByCompanyId(@Param("companyId") Long companyId);
    
    // Contar usuarios por tipo de aplicación
    @Query("SELECT COUNT(s) FROM SiigoApiUser s WHERE s.appType = :appType")
    Long countByAppType(@Param("appType") String appType);
    
    // Buscar por email que contenga un texto específico
    List<SiigoApiUser> findByEmailContaining(String emailPattern);
}
