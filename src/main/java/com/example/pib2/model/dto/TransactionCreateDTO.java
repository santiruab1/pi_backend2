package com.example.pib2.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionCreateDTO {
    
    @NotNull(message = "El ID del documento es obligatorio")
    private Long documentId;

    @NotNull(message = "El ID del almacén es obligatorio")
    private Long warehouseId;
}