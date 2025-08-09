package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "warehouse_id", nullable = false, unique = true)
    private Long id;
    @Column(name = "warehouse_name", nullable = false, length = 100)
    private String name;



}
