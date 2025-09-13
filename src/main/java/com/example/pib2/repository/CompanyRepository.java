package com.example.pib2.repository;

import com.example.pib2.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Búsquedas por campos únicos
    Optional<Company> findByNit(String nit);
    Optional<Company> findByCompanyName(String companyName);
    
    // Verificaciones de existencia
    boolean existsByNit(String nit);
    boolean existsByCompanyName(String companyName);
    
    // Búsquedas por estado
    List<Company> findByStatus(String status);
    List<Company> findByStatusAndType(String status, String type);
    
    // Búsquedas por tipo
    List<Company> findByType(String type);
    
    // Búsquedas por ubicación
    List<Company> findByCity(String city);
    List<Company> findByCountry(String country);
    
    // Búsquedas combinadas
    Optional<Company> findByNitAndStatus(String nit, String status);
    List<Company> findByTypeAndCity(String type, String city);
}
