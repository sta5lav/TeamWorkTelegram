package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.CatShelterClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatShelterClientRepository extends JpaRepository<CatShelterClient, Long> {

    Optional<CatShelterClient> findByUserId(long chatId);

    Boolean existsByUserId(long chatId);
}
