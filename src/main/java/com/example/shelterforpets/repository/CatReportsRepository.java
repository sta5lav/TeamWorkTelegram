package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.CatReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatReportsRepository extends JpaRepository<CatReport, Long> {

    CatReport findByUserId(long userId);

    List<CatReport> findAllByUserId(long chatId);
}
