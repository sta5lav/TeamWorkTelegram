package com.example.shelterforpets.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "dog_report")
public class DogReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "petreport", columnDefinition = "TEXT")
    private String petReport;

    @Column(name = "photopet", columnDefinition = "BYTEA")
    private byte[] photoPet;

    @Column(name = "datereport")
    private LocalDateTime dateReport;

}