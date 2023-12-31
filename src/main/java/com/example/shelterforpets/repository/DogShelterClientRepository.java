package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.DogShelterClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DogShelterClientRepository extends JpaRepository<DogShelterClient, Long> {
    Boolean existsByUserId(long chatId);

    Optional<DogShelterClient> findByUserId(long chatId);

    DogShelterClient findDogShelterClientByUserId(long id);

}
