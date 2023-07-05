package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Volunteer findAllById(long chatId);

}