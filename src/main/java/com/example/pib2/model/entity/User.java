package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false, length = 100)
    private String user_first_name;

    @Column(nullable = false, length = 100)
    private String user_sur_name;

    @Column(nullable = false, length = 200,unique = true)
    private String user_email;

    @Column(nullable = false)
    private String user_password;

}
