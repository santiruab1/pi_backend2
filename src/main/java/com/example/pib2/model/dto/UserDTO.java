package com.example.pib2.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    
    private Long id;
    private String identificationNumber, firstName, userSurName, userEmail;

    public UserDTO() {}

}
