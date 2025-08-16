package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplier_id;
    @Column(name = "supplier_identification_number", nullable = false, unique = true, length = 20)
    private String identificationNumber;
    @Column(name = "supplier_name", nullable = false, length = 120)
    private String name;


}
