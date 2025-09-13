package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"transactions"})
@Entity
@Table(name = "warehouses")
public class Warehouse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id", nullable = false, unique = true)
    private Long id;


    @Column(name = "warehouse_name", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Transaction> transactions;

}
