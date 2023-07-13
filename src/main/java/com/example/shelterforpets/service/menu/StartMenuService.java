package com.example.shelterforpets.service.menu;

import com.example.shelterforpets.entity.Step;
import com.example.shelterforpets.service.CatShelterService;
import com.example.shelterforpets.service.DogShelterService;
import com.example.shelterforpets.service.ShelterService;
import org.springframework.stereotype.Service;

import static com.example.shelterforpets.constants.Constants.*;

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
        if (message.equals(CAT_SHELTER)) {
            catShelterService.catShelterMenu(chatId);
            shelterService.saveClient(chatId, Step.CAT_SHELTER_MENU);
            shelterService.saveClientInCatShelter(chatId);
        } else if (message.equals(DOG_SHELTER)) {
            dogShelterService.dogShelterMenu(chatId);
            shelterService.saveClient(chatId, Step.DOG_SHELTER_MENU);
            shelterService.saveClientInDogShelter(chatId);
        }
    }
}
