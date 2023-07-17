package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByUserId(long chatId);

    Boolean existsByUserId(long chatId);
}
