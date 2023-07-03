package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.DogReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogReportsRepository extends JpaRepository<DogReport, Long> {



}
