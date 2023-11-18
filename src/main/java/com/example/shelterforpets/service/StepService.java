package com.example.shelterforpets.service;

import com.example.shelterforpets.constants.Step;
import com.example.shelterforpets.listener.TelegramBotUpdatesListener;
import com.example.shelterforpets.service.menu.CatMenuService;
import com.example.shelterforpets.service.menu.DogMenuService;
import com.example.shelterforpets.service.menu.StartMenuService;
import com.pengrad.telegrambot.model.Message;
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
        Message chatMessage = update.message();
        if (chatMessage != null) {
            long chatId = update.message().chat().id();
            String message = update.message().text();
            // Process your updates here
            if ("/start".equals(message)) {
                //shelterService.sendWelcomeMessage(chatId);
                shelterService.sendShelterMenu(chatId, "Привет! Я бот, и я готов помочь Вам." +
                        " Какой приют Вас интересует?");
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
                    case CAT_SHELTER_CONSULTATION_MENU:
                        catMenuService.catShelterConsultantMenu(chatId, message);
                        break;
                    case DOG_SHELTER_CONSULTATION_MENU:
                        dogMenuService.dogShelterConsultantMenu(chatId, message);
                        break;
                    case DOG_SHELTER_REPORT_MENU:
                        dogMenuService.dogShelterReportMenu(update);
                        break;
                    case CAT_SHELTER_REPORT_MENU:
                        catMenuService.catShelterReportMenu(update);
                        break;
                }
            }
        }
    }
}
