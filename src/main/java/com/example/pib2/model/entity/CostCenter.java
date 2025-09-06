package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cost_centers")
public class CostCenter {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name ="cost_center_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "cost_center_name", nullable = false, length = 100)
    private String name;
}
