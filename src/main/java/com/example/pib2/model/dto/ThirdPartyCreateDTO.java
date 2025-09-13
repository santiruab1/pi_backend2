package com.example.pib2.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ThirdPartyCreateDTO {
    
    @NotBlank(message = "El documento del tercero es obligatorio")
    @Size(max = 20, message = "El documento no puede exceder 20 caracteres")
    private String document;

    @NotBlank(message = "El nombre del tercero es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String name;

    @NotBlank(message = "El NIT es obligatorio")
    @Size(max = 20, message = "El NIT no puede exceder 20 caracteres")
    private String taxId;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 300, message = "La dirección no puede exceder 300 caracteres")
    private String address;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String phone;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;

    @NotBlank(message = "El tipo de tercero es obligatorio")
    @Size(max = 50, message = "El tipo no puede exceder 50 caracteres")
    private String type;
}