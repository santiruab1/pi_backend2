package com.example.pib2.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DocumentCreateDTO {
    
    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer documentTypeId;

    @NotNull(message = "La fecha del documento es obligatoria")
    private LocalDate documentDate;

    @NotNull(message = "La fecha de recepción es obligatoria")
    private LocalDate documentReception;

    @NotBlank(message = "El prefijo del documento es obligatorio")
    @Size(max = 10, message = "El prefijo no puede exceder 10 caracteres")
    private String documentPrefix;

    @NotBlank(message = "El número del documento es obligatorio")
    @Size(max = 50, message = "El número no puede exceder 50 caracteres")
    private String documentNumber;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate documentDueDate;

    @NotNull(message = "El ID del tercero es obligatorio")
    private Long thirdPartyId;
}