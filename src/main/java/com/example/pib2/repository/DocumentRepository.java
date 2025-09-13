package com.example.pib2.repository;

import com.example.pib2.model.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    // Búsquedas por número de documento
    Optional<Document> findByNumber(String number);
    Optional<Document> findByNumberAndType(String number, String type);
    boolean existsByNumber(String number);
    boolean existsByNumberAndType(String number, String type);

    // Búsquedas por tipo de documento
    List<Document> findByType(String type);
    Page<Document> findByType(String type, Pageable pageable);

    // Búsquedas por fecha de emisión
    List<Document> findByIssueDateBetween(LocalDate startDate, LocalDate endDate);
    Page<Document> findByIssueDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // Búsquedas por estado
    List<Document> findByStatus(String status);
    Page<Document> findByStatus(String status, Pageable pageable);

    // Búsquedas por tercero
    List<Document> findByThirdPartyId(Long thirdPartyId);
    Page<Document> findByThirdPartyId(Long thirdPartyId, Pageable pageable);

    // Búsquedas combinadas
    List<Document> findByTypeAndStatus(String type, String status);
    List<Document> findByTypeAndThirdPartyId(String type, Long thirdPartyId);
    List<Document> findByThirdPartyIdAndIssueDateBetween(Long thirdPartyId, LocalDate startDate, LocalDate endDate);

    // Validaciones de negocio
    @Query("SELECT COUNT(d) > 0 FROM Document d WHERE " +
           "d.type = :type AND d.number = :number AND (:id IS NULL OR d.id != :id)")
    boolean existsSimilarDocument(String type, String number, Long id);

    // Búsquedas personalizadas
    @Query("SELECT d FROM Document d WHERE " +
           "LOWER(d.number) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(d.type) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(d.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Document> searchDocuments(String searchTerm, Pageable pageable);

    // Búsquedas por monto (si aplica)
    @Query("SELECT d FROM Document d WHERE d.amount BETWEEN :minAmount AND :maxAmount")
    List<Document> findByAmountRange(Double minAmount, Double maxAmount);

    // Conteos y estadísticas
    long countByType(String type);
    long countByStatus(String status);
    long countByThirdPartyId(Long thirdPartyId);
    
    @Query("SELECT COUNT(d) FROM Document d WHERE d.issueDate BETWEEN :startDate AND :endDate")
    long countDocumentsInPeriod(LocalDate startDate, LocalDate endDate);

    // Validaciones avanzadas
    @Query("SELECT d FROM Document d WHERE " +
           "d.type = :type AND " +
           "EXTRACT(YEAR FROM d.issueDate) = :year AND " +
           "EXTRACT(MONTH FROM d.issueDate) = :month")
    List<Document> findByTypeAndPeriod(String type, int year, int month);
}
