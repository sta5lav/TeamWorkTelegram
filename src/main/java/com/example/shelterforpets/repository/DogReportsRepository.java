package com.example.shelterforpets.repository;

import com.example.shelterforpets.entity.DogReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DogReportsRepository extends JpaRepository<DogReport, Long> {


    DogReport findByUserId(long userId);

    List<DogReport> findAllByUserId(long chatId);

}
