package com.example.pib2.model.dto;

import lombok.Data;

@Data
public class SiigoApiUserCreateDTO {
    
    private String email;
    private String accessKey;
    private String appType;
    private Long userId;
    private Long companyId;
}
