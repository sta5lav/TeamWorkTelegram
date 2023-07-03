package com.example.shelterforpets.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cat_report")
public class CatReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "petreport", columnDefinition = "TEXT", nullable = false)
    private String petReport;

    @Column(name = "photopet", columnDefinition = "BYTEA", nullable = false)
    private byte[] photoPet;

}
