package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cost_centers")
public class CostCenter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name ="cost_center_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "cost_center_name", nullable = false, length = 100)
    private String name;
}
