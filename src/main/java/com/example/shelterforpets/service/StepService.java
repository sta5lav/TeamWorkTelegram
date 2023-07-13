package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.Step;
import com.example.shelterforpets.listener.TelegramBotUpdatesListener;
import com.example.shelterforpets.service.menu.CatMenuService;
import com.example.shelterforpets.service.menu.DogMenuService;
import com.example.shelterforpets.service.menu.StartMenuService;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StepService {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final ShelterService shelterService;
    private final StartMenuService startMenuService;
    private final CatMenuService catMenuService;
    private final DogMenuService dogMenuService;

    public StepService(ShelterService shelterService,
                       StartMenuService startMenuService, CatMenuService catMenuService, DogMenuService dogMenuService) {
        this.shelterService = shelterService;
        this.startMenuService = startMenuService;
        this.catMenuService = catMenuService;
        this.dogMenuService = dogMenuService;
    }

    public void process(Update update) {
        logger.info("Processing update: {}", update);

        long chatId = update.message().chat().id();
        String message = update.message().text();
        if (update.message().text() == null) {
            logger.warn("Skip message because text is null");
            return;
        }
        // Process your updates here
        if (message.equals("/start")) {
            shelterService.sendWelcomeMessage(chatId);
            shelterService.saveClient(chatId, Step.START_MENU);
            //выбор приюта
        } else {
            Step step = shelterService.getStepClient(chatId);
            switch (step) {
                case START_MENU:
                    startMenuService.startMenu(chatId, message);
                    break;
                case CAT_SHELTER_MENU:
                    catMenuService.catShelterMenu(update, chatId, message);
                    break;
                case DOG_SHELTER_MENU:
                    dogMenuService.dogShelterMenu(update, chatId, message);
                    break;
                case CAT_SHELTER_INFO_MENU:
                    catMenuService.catShelterInfoMenu(chatId, message);
                    break;
                case DOG_SHELTER_INFO_MENU:
                    dogMenuService.dogShelterInfoMenu(chatId, message);
                    break;
                /*
                Обработка новых степов

                case CAT_SHELTER_CONSULTATION_MENU:
                    catMenuService....вызвать нужный метод;
                    break;
                case DOG_SHELTER_CONSULTATION_MENU:
                    dogMenuService....вызвать нужный метод;
                    break;
                 */

            }
        }
    }
}
