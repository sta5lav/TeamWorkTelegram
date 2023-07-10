package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.service.CatShelterService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatShelterClientRepository extends JpaRepository<CatShelterClient, Long> {

    Optional<CatShelterClient> findByUserId(long chatId);

    Boolean existsAllByUserId(long chatId);

    CatShelterClient findAllByUserId(long chatId);

}
