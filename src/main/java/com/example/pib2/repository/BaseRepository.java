package com.example.pib2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    
    // Métodos para manejo de estado
    @Query("SELECT e FROM #{#entityName} e WHERE e.active = true")
    List<T> findAllActive();
    
    @Query("SELECT e FROM #{#entityName} e WHERE e.active = false")
    List<T> findAllInactive();
    
    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.active = false WHERE e.id = :id")
    void softDelete(ID id);
    
    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.active = true WHERE e.id = :id")
    void restore(ID id);
    
    // Métodos para auditoría
    @Query("SELECT e FROM #{#entityName} e WHERE e.createdAt BETWEEN :start AND :end")
    List<T> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT e FROM #{#entityName} e WHERE e.updatedAt BETWEEN :start AND :end")
    List<T> findByUpdatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    // Métodos de búsqueda por creador/modificador
    @Query("SELECT e FROM #{#entityName} e WHERE e.createdBy = :userId")
    List<T> findByCreatedBy(Long userId);
    
    @Query("SELECT e FROM #{#entityName} e WHERE e.updatedBy = :userId")
    List<T> findByUpdatedBy(Long userId);
    
    // Métodos de validación
    @Query("SELECT COUNT(e) > 0 FROM #{#entityName} e WHERE e.id = :id AND e.active = true")
    boolean existsAndIsActive(ID id);
    
    // Métodos de búsqueda seguros (solo activos)
    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.active = true")
    Optional<T> findByIdAndActive(ID id);
    
    // Métodos para conteos y estadísticas
    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.active = true")
    long countActive();
    
    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.active = false")
    long countInactive();
    
    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.createdAt BETWEEN :start AND :end")
    long countCreatedBetween(LocalDateTime start, LocalDateTime end);
    
    // Métodos para limpieza y mantenimiento
    @Modifying
    @Transactional
    @Query("DELETE FROM #{#entityName} e WHERE e.active = false AND e.updatedAt < :date")
    void deleteInactiveBefore(LocalDateTime date);
    
    // Métodos de búsqueda por fecha y estado
    @Query("SELECT e FROM #{#entityName} e WHERE e.active = :active AND e.createdAt BETWEEN :start AND :end")
    List<T> findByActiveAndCreatedAtBetween(boolean active, LocalDateTime start, LocalDateTime end);
    
    // Métodos de actualización masiva
    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.active = :active WHERE e.id IN :ids")
    void updateActiveStatus(List<ID> ids, boolean active);
    
    // Métodos para verificación de integridad
    @Query("SELECT e FROM #{#entityName} e WHERE e.updatedAt < e.createdAt")
    List<T> findInconsistentDates();
    
    @Query("SELECT e FROM #{#entityName} e WHERE e.createdBy IS NULL OR e.updatedBy IS NULL")
    List<T> findMissingAuditInfo();
}
