package com.example.pib2.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ThirdPartyDTO {
    
    private Long id;
    private String document;
    private String name;
    private String taxId;
    private String address;
    private String phone;
    private String email;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}