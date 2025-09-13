package com.example.pib2.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyCreateDTO {
    
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 120, message = "El nombre no puede exceder 120 caracteres")
    private String name;

    @NotBlank(message = "El número de identificación es obligatorio")
    @Size(max = 20, message = "El número de identificación no puede exceder 20 caracteres")
    private String identificationNumber;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String address;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    private String phone;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
}