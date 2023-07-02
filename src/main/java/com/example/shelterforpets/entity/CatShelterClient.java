package com.example.shelterforpets.entity;


import javax.persistence.*;

@Entity
@Table(name = "catShelterClient")
public class CatShelterClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;






}
