package com.example.shelterforpets.entity;

import com.example.shelterforpets.constants.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Table(name = "dog_shelter_client")
public class DogShelterClient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(name = "phonenumber", columnDefinition = "TEXT")
    private String phoneNumber;

    @Column(name = "nicknamepet", columnDefinition = "TEXT")
    private String nickNamePet;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

}
