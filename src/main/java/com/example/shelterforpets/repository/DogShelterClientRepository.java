package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.DogShelterClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogShelterClientRepository extends JpaRepository<DogShelterClient, Long> {

    DogShelterClient findAllByUserId(long chatId);
    Boolean existsAllByUserId(long chatId);

}
