package com.example.pib2.model.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UserDTO {
    
    private Long id;
    private String identificationNumber;
    private String firstName;
    private String userSurName;
    private String userEmail;
    private String userPhoneNumber;
    private Date userCreatedAt;

    public UserDTO() {}

}
