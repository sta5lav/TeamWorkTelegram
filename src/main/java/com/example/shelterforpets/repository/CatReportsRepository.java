package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.CatReport;
import com.example.shelterforpets.entity.DogReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatReportsRepository extends JpaRepository<CatReport, Long> {

    CatReport findAllByUserId(Long id);

}
