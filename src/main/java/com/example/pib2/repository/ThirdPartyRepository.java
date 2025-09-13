package com.example.pib2.repository;

import com.example.pib2.model.entity.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
    // Búsquedas por documentos
    Optional<ThirdParty> findByDocumentNumber(String documentNumber);
    Optional<ThirdParty> findByDocumentNumberAndDocumentType(String documentNumber, String documentType);
    boolean existsByDocumentNumber(String documentNumber);

    // Búsquedas por nombre
    Optional<ThirdParty> findByName(String name);
    List<ThirdParty> findByNameContainingIgnoreCase(String name);

    // Búsquedas por email
    Optional<ThirdParty> findByEmail(String email);
    boolean existsByEmail(String email);

    // Búsquedas por tipo de documento
    List<ThirdParty> findByDocumentType(String documentType);
    
    // Búsquedas combinadas
    List<ThirdParty> findByDocumentTypeAndNameContainingIgnoreCase(String documentType, String name);
    List<ThirdParty> findByEmailContainingIgnoreCase(String email);

}
