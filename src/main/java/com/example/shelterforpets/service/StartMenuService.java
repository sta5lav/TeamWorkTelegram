package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.Step;
import org.springframework.stereotype.Service;

@Service
public class StartMenuService {

    private final ShelterService shelterService;
    private final CatShelterService catShelterService;
    private final DogShelterService dogShelterService;

    public StartMenuService(ShelterService shelterService,
                            CatShelterService catShelterService,
                            DogShelterService dogShelterService) {
        this.shelterService = shelterService;
        this.catShelterService = catShelterService;
        this.dogShelterService = dogShelterService;
    }

    public void startMenu(long chatId, String message) {
        if (message.equals("Приют для кошек")) {
            catShelterService.catShelterMenu(chatId);
            shelterService.saveClient(chatId, Step.CAT_SHELTER_MENU);
            shelterService.saveClientInCatShelter(chatId);
        } else if (message.equals("Приют для собак")) {
            dogShelterService.dogShelterMenu(chatId);
            shelterService.saveClient(chatId, Step.DOG_SHELTER_MENU);
            shelterService.saveClientInDogShelter(chatId);
        }
    }
}
