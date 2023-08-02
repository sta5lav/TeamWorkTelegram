package com.example.shelterforpets.timer;

import com.example.shelterforpets.service.DogShelterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NotificationTimerADogShelterTests {
    private NotificationTimerADogShelter notificationTimerADogShelter;
    @Mock
    private DogShelterService dogShelterService;

    @BeforeEach
    public void init() {
        notificationTimerADogShelter = new NotificationTimerADogShelter(dogShelterService);
    }

    @Test
    public void testSendWarningMessage() {
        notificationTimerADogShelter.sendWarningMessage();

        verify(dogShelterService).findClientsWithAnOverdueDateOfReports();
    }
}
