package com.example.pib2.model.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DocumentDTO {
    
    private Long id;
    private Integer documentTypeId;
    private LocalDate documentDate;
    private LocalDate documentReception;
    private String documentPrefix;
    private String documentNumber;
    private LocalDate documentDueDate;
    private Long thirdPartyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}