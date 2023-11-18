package com.example.shelterforpets.entity;

import com.example.shelterforpets.constants.Step;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "step")
    @Enumerated(EnumType.STRING)
    private Step step;
}
