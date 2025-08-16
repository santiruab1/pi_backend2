package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "third_parties")
public class ThirdParty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "third_party_id", nullable = false, unique = true)
    private Long id;

    @Column (name = "third_party_document", nullable = false, unique = true)
    private String document;

    @Column(name = "third_party_name", nullable = false)
    private String name;

    @Column(name = "third_party_tax_id", nullable = false)
    private String taxId;

}
