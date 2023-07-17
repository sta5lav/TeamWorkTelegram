package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.CatReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CatReportsRepository extends JpaRepository<CatReport, Long> {

    CatReport findByUserIdAndDateReport(long userId, LocalDateTime localDate);

    CatReport findByUserId(long userId);

}
