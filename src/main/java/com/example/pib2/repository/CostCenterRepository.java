package com.example.pib2.repository;

import com.example.pib2.model.entity.CostCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {
    // Búsquedas por código (único)
    Optional<CostCenter> findByCode(String code);
    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, Long id);

    // Búsquedas por nombre
    Optional<CostCenter> findByName(String name);
    List<CostCenter> findByNameContainingIgnoreCase(String name);
    Page<CostCenter> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Búsquedas por estado
    List<CostCenter> findByStatus(String status);
    Page<CostCenter> findByStatus(String status, Pageable pageable);

    // Búsquedas por descripción
    List<CostCenter> findByDescriptionContainingIgnoreCase(String description);

    // Búsquedas combinadas
    List<CostCenter> findByStatusAndNameContainingIgnoreCase(String status, String name);
    Page<CostCenter> findByStatusAndNameContainingIgnoreCase(String status, String name, Pageable pageable);

    // Búsquedas jerárquicas (si aplica estructura padre-hijo)
    List<CostCenter> findByParentId(Long parentId);
    List<CostCenter> findByParentIsNull();

    // Validaciones de negocio
    @Query("SELECT COUNT(cc) > 0 FROM CostCenter cc WHERE LOWER(cc.code) = LOWER(:code) AND (:id IS NULL OR cc.id != :id)")
    boolean existsSimilarCode(String code, Long id);

    // Búsquedas personalizadas
    @Query("SELECT cc FROM CostCenter cc WHERE " +
           "LOWER(cc.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(cc.code) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(cc.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<CostCenter> searchCostCenters(String searchTerm, Pageable pageable);

    // Conteos y estadísticas
    long countByStatus(String status);
    long countByParentId(Long parentId);
}
