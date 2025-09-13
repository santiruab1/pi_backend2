package com.example.pib2.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    
    private Long id;
    private Long documentId;
    private Long warehouseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}