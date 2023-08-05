package com.example.shelterforpets.timer;

import com.example.shelterforpets.service.CatShelterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NotificationTimerACatShelterTests {
    private NotificationTimerACatShelter notificationTimerACatShelter;
    @Mock
    private CatShelterService catShelterService;

    @BeforeEach
    public void init() {
        notificationTimerACatShelter = new NotificationTimerACatShelter(catShelterService);
    }

    @Test
    public void testSendWarningMessage() {
        notificationTimerACatShelter.sendWarningMessage();

        verify(catShelterService).findClientsWithAnOverdueDateOfReports();
    }
}
