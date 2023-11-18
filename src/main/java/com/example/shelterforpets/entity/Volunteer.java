package com.example.shelterforpets.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "volunteer")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(name = "phonenumber", columnDefinition = "TEXT")
    private String phoneNumber;

}
