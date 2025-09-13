package com.example.pib2.repository;

import com.example.pib2.model.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    // Búsquedas por nombre
    Optional<Warehouse> findByName(String name);
    List<Warehouse> findByNameContainingIgnoreCase(String name);
    Page<Warehouse> findByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

    // Búsquedas por ubicación
    List<Warehouse> findByLocation(String location);
    List<Warehouse> findByLocationContainingIgnoreCase(String location);
    Page<Warehouse> findByLocationContainingIgnoreCase(String location, Pageable pageable);

    // Búsquedas por capacidad
    List<Warehouse> findByCapacityGreaterThanEqual(Integer minCapacity);
    List<Warehouse> findByCapacityLessThanEqual(Integer maxCapacity);
    List<Warehouse> findByCapacityBetween(Integer minCapacity, Integer maxCapacity);

    // Búsquedas por estado
    List<Warehouse> findByStatus(String status);
    Page<Warehouse> findByStatus(String status, Pageable pageable);

    // Búsquedas combinadas
    List<Warehouse> findByLocationAndStatus(String location, String status);
    Page<Warehouse> findByStatusAndCapacityGreaterThan(String status, Integer minCapacity, Pageable pageable);

    // Validaciones de capacidad
    @Query("SELECT CASE WHEN w.currentOccupancy + :additionalSpace <= w.capacity THEN true ELSE false END " +
           "FROM Warehouse w WHERE w.id = :warehouseId")
    boolean hasAvailableSpace(Long warehouseId, Integer additionalSpace);

    // Búsquedas por disponibilidad
    @Query("SELECT w FROM Warehouse w WHERE (w.capacity - w.currentOccupancy) >= :requiredSpace")
    List<Warehouse> findWithAvailableSpace(Integer requiredSpace);

    // Búsquedas por utilización
    @Query("SELECT w FROM Warehouse w WHERE (CAST(w.currentOccupancy AS float) / w.capacity) * 100 >= :utilizationPercentage")
    List<Warehouse> findByUtilizationPercentageGreaterThan(Double utilizationPercentage);

    // Búsquedas personalizadas
    @Query("SELECT w FROM Warehouse w WHERE " +
           "LOWER(w.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(w.location) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(w.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Warehouse> searchWarehouses(String searchTerm, Pageable pageable);

    // Conteos y estadísticas
    long countByStatus(String status);
    
    @Query("SELECT COUNT(w) FROM Warehouse w WHERE w.currentOccupancy >= :occupancyThreshold")
    long countHighOccupancyWarehouses(Integer occupancyThreshold);

    @Query("SELECT SUM(w.capacity) FROM Warehouse w WHERE w.status = :status")
    Integer getTotalCapacityByStatus(String status);

    @Query("SELECT SUM(w.currentOccupancy) FROM Warehouse w WHERE w.status = :status")
    Integer getTotalOccupancyByStatus(String status);

    // Validaciones de negocio
    @Query("SELECT COUNT(w) > 0 FROM Warehouse w WHERE " +
           "LOWER(w.name) = LOWER(:name) AND LOWER(w.location) = LOWER(:location) AND (:id IS NULL OR w.id != :id)")
    boolean existsSimilarWarehouse(String name, String location, Long id);
}
