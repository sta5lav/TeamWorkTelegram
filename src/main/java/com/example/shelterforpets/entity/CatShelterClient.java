package com.example.shelterforpets.entity;

import com.example.shelterforpets.constants.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cat_shelter_client")
public class CatShelterClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "phonenumber", columnDefinition = "TEXT")
    private String phoneNumber;

    @Column(name = "nicknamepet", columnDefinition = "TEXT")
    private String nickNamePet;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

}
