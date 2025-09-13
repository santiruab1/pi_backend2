package com.example.pib2.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CompanyDTO {
    
    private Long id;
    private String name;
    private String identificationNumber;
    private String address;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}