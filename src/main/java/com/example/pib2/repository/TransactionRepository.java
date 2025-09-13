package com.example.pib2.repository;

import com.example.pib2.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Búsquedas por tipo de transacción
    List<Transaction> findByType(String type);
    Page<Transaction> findByType(String type, Pageable pageable);

    // Búsquedas por estado
    List<Transaction> findByStatus(String status);
    Page<Transaction> findByStatus(String status, Pageable pageable);

    // Búsquedas por fecha
    List<Transaction> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Page<Transaction> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Búsquedas por monto
    List<Transaction> findByAmountGreaterThanEqual(BigDecimal minAmount);
    List<Transaction> findByAmountLessThanEqual(BigDecimal maxAmount);
    List<Transaction> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Búsquedas por usuario
    List<Transaction> findByUserId(Long userId);
    Page<Transaction> findByUserId(Long userId, Pageable pageable);

    // Búsquedas por centro de costo
    List<Transaction> findByCostCenterId(Long costCenterId);
    Page<Transaction> findByCostCenterId(Long costCenterId, Pageable pageable);

    // Búsquedas por almacén
    List<Transaction> findByWarehouseId(Long warehouseId);
    Page<Transaction> findByWarehouseId(Long warehouseId, Pageable pageable);

    // Búsquedas combinadas
    List<Transaction> findByTypeAndStatus(String type, String status);
    List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findByCostCenterIdAndDateBetween(Long costCenterId, LocalDateTime startDate, LocalDateTime endDate);

    // Búsquedas con agregación
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.status = :status")
    BigDecimal sumAmountByStatus(String status);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByTypeAndDateRange(String type, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.costCenterId = :costCenterId AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByCostCenterAndDateRange(Long costCenterId, LocalDateTime startDate, LocalDateTime endDate);

    // Búsquedas por referencia de documento
    List<Transaction> findByDocumentId(Long documentId);
    Optional<Transaction> findByDocumentIdAndType(Long documentId, String type);

    // Conteos y estadísticas
    long countByType(String type);
    long countByStatus(String status);
    long countByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.costCenterId = :costCenterId AND t.amount > :amount")
    long countTransactionsAboveAmountByCostCenter(Long costCenterId, BigDecimal amount);

    // Validaciones de negocio
    boolean existsByDocumentIdAndType(Long documentId, String type);
    
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Transaction t " +
           "WHERE t.documentId = :documentId AND t.status = 'COMPLETED'")
    boolean hasCompletedTransactionForDocument(Long documentId);

    // Búsquedas personalizadas
    @Query("SELECT t FROM Transaction t WHERE " +
           "t.type = :type AND " +
           "t.status = :status AND " +
           "t.date BETWEEN :startDate AND :endDate AND " +
           "t.amount BETWEEN :minAmount AND :maxAmount")
    List<Transaction> findByMultipleCriteria(
        String type,
        String status,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal minAmount,
        BigDecimal maxAmount
    );
}