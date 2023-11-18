package com.example.shelterforpets.timer;

import com.example.shelterforpets.service.CatShelterService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationTimerACatShelter {

    private final CatShelterService catShelterService;


    public NotificationTimerACatShelter(CatShelterService catShelterService) {
        this.catShelterService = catShelterService;
    }

    @Scheduled(cron = "0 0 21 * * *")
    public void sendWarningMessage() {
        catShelterService.findClientsWithAnOverdueDateOfReports();
    }
}
