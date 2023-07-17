package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Boolean existsByUserId(long chatId);

    //Optional<Volunteer> findByUserId(long chatId);
    Optional<Volunteer> findByUserId(long chatId);

}
