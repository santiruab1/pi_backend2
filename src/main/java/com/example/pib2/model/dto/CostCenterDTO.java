package com.example.pib2.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CostCenterDTO {
    
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}