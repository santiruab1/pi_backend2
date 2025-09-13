package com.example.pib2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "third_parties")
public class ThirdParty extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "third_party_id", nullable = false, unique = true)
    private Long Id;

    @Column (name = "third_party_document", nullable = false, unique = true)
    private String Document;

    @Column(name = "third_party_name", nullable = false)
    private String Name;

    @Column(name = "third_party_tax_id", nullable = false)
    private String TaxId;

    @Column(name = "third_party_address", nullable = false)
    private String Address;

    @Column(name = "third_party_phone", nullable = false)
    private String Phone;

    @Column(name = "third_party_email", nullable = false)
    private String Email;

    @Column(name = "third_party_type", nullable = false)
    private String Type;

}
