package com.example.shelterforpets.entity;

import javax.persistence.*;

@Entity
@Table(name = "dogShelterClient")
public class DogShelterClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
