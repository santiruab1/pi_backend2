package com.example.pib2.repository;

import com.example.pib2.model.entity.UserCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserCompanyRepository extends JpaRepository<UserCompany, Long> {
    // Búsquedas por usuario
    List<UserCompany> findByUserId(Long userId);
    Page<UserCompany> findByUserId(Long userId, Pageable pageable);
    
    // Búsquedas por compañía
    List<UserCompany> findByCompanyId(Long companyId);
    Page<UserCompany> findByCompanyId(Long companyId, Pageable pageable);
    
    // Búsqueda específica usuario-compañía
    Optional<UserCompany> findByUserIdAndCompanyId(Long userId, Long companyId);
    boolean existsByUserIdAndCompanyId(Long userId, Long companyId);
    
    // Búsquedas por rol en la compañía
    List<UserCompany> findByCompanyIdAndRole(Long companyId, String role);
    List<UserCompany> findByUserIdAndRole(Long userId, String role);
    
    // Búsquedas por estado
    List<UserCompany> findByCompanyIdAndStatus(Long companyId, String status);
    List<UserCompany> findByUserIdAndStatus(Long userId, String status);
    
    // Búsquedas por fecha
    List<UserCompany> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<UserCompany> findByCompanyIdAndCreatedAtBetween(Long companyId, LocalDateTime start, LocalDateTime end);
    
    // Operaciones de actualización
    @Modifying
    @Transactional
    @Query("UPDATE UserCompany uc SET uc.status = :status WHERE uc.userId = :userId AND uc.companyId = :companyId")
    void updateStatus(Long userId, Long companyId, String status);
    
    @Modifying
    @Transactional
    @Query("UPDATE UserCompany uc SET uc.role = :role WHERE uc.userId = :userId AND uc.companyId = :companyId")
    void updateRole(Long userId, Long companyId, String role);
    
    // Operaciones de eliminación
    @Modifying
    @Transactional
    void deleteByUserIdAndCompanyId(Long userId, Long companyId);
    
    @Modifying
    @Transactional
    void deleteByCompanyId(Long companyId);
    
    // Consultas personalizadas
    @Query("SELECT uc FROM UserCompany uc WHERE uc.companyId = :companyId AND uc.role IN :roles")
    List<UserCompany> findByCompanyIdAndRoles(Long companyId, List<String> roles);
    
    @Query("SELECT COUNT(uc) > 0 FROM UserCompany uc WHERE uc.userId = :userId AND uc.companyId = :companyId AND uc.role = :role")
    boolean hasRole(Long userId, Long companyId, String role);
    
    // Conteos y estadísticas
    long countByCompanyId(Long companyId);
    long countByCompanyIdAndRole(Long companyId, String role);
    
    @Query("SELECT COUNT(uc) FROM UserCompany uc WHERE uc.companyId = :companyId AND uc.status = 'ACTIVE'")
    long countActiveUsersByCompany(Long companyId);
}
