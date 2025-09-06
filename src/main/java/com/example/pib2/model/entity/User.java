package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "users") // Nombre de la entidad en snake_case
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_identification_number", nullable = false, length = 50, unique = true)
    private String identificationNumber;

    @Column(name = "user_first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "user_sur_name", nullable = false, length = 100)
    private String userSurName;

    @Column(name = "user_email", nullable = false, length = 200, unique = true)
    private String userEmail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_phone_number", nullable = false, length = 20)
    private String userPhoneNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_created_at", nullable = false)
    private Date userCreatedAt;

    // @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    // private java.util.List<UserCompany> userCompanies;


}
