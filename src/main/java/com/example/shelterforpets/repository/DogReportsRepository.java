package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.DogReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DogReportsRepository extends JpaRepository<DogReport, Long> {


    DogReport findAllByUserId(Long id);

    DogReport findByUserIdAndDateReport(long userId, LocalDateTime dateReport);
}
