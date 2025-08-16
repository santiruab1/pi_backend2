package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity // This annotation marks the class as a JPA entity (a table in the database)
@Data // Lombok will generate getters, setters, toString, equals, and hashCode methods

public class AppUser {
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

    @Column(nullable = false, length = 20)
    private String user_phone_number;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name ="user_created_at", nullable = false)
    private Date user_created_at;


}
