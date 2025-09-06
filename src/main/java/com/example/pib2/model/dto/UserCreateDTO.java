package com.example.pib2.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDTO {
    @NotBlank
    @Size(max = 50)
    private String identificationNumber;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String userSurName;

    @NotBlank
    @Email
    @Size(max = 200)
    private String userEmail;

    @NotBlank
    @Size(min = 6, max = 100)
    private String userPassword;

    @NotBlank
    @Size(max = 20)
    private String userPhoneNumber;

}
