package com.example.shelterforpets.timer;

import com.example.shelterforpets.service.DogShelterService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class NotificationTimerADogShelter {

    private final DogShelterService dogShelterService;



    public NotificationTimerADogShelter(DogShelterService dogShelterService) {
        this.dogShelterService = dogShelterService;
    }

    @Scheduled(cron = "0 0 21 * * *")
    public void sendWarningMessage() {
        dogShelterService.findClientsWithAnOverdueDateOfReports();
    }
}
