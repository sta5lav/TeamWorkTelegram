package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Volunteer findAllByUserId(long chatId);

    Boolean existsAllByUserId(long chatId);

}
