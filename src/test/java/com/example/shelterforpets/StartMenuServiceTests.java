package com.example.shelterforpets;

import com.example.shelterforpets.entity.Step;
import com.example.shelterforpets.service.CatShelterService;
import com.example.shelterforpets.service.DogShelterService;
import com.example.shelterforpets.service.ShelterService;
import com.example.shelterforpets.service.StartMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class StartMenuServiceTests {
    private StartMenuService startMenuService;
    @Mock
    private ShelterService shelterService;
    @Mock
    private CatShelterService catShelterService;
    @Mock
    private DogShelterService dogShelterService;

    @BeforeEach
    public void init() {
        startMenuService = new StartMenuService(shelterService, catShelterService, dogShelterService);
    }

    @Test
    public void testStartMenu_ChooseCatShelter() {
        String message = "Приют для кошек";
        long chatId = 1238L;

        startMenuService.startMenu(chatId, message);

        verify(catShelterService).catShelterMenu(chatId);
        verify(shelterService).saveClient(chatId, Step.CAT_SHELTER_MENU);
        verify(shelterService).saveClientInCatShelter(chatId);
    }

    @Test
    public void testStartMenu_ChooseDogShelter() {
        String message = "Приют для собак";
        long chatId = 1238L;

        startMenuService.startMenu(chatId, message);

        verify(dogShelterService).dogShelterMenu(chatId);
        verify(shelterService).saveClient(chatId, Step.DOG_SHELTER_MENU);
        verify(shelterService).saveClientInDogShelter(chatId);
    }

    @Test
    public void testStartMenu_OtherMessage_DoesNothing() {
        String message = "text";
        long chatId = 1238L;

        startMenuService.startMenu(chatId, message);

        verifyNoInteractions(catShelterService, dogShelterService, shelterService);
    }
}
