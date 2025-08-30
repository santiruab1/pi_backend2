package com.example.pib2.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    
    private Long id;
    private String firstName, userSurName, userEmail;

    public UserDTO() {}

}
