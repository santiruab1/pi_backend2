package com.example.pib2.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SiigoApiUserDTO {
    
    private Long id;
    private String email;
    private String accessKey;
    private String appType;
    private Date createdAt;
    private Date updatedAt;
    private Long userId;
    private Long companyId;
}
