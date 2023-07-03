package com.example.shelterforpets.listener;

import com.example.shelterforpets.service.MessageService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service

public class TelegramBotUpdatesListener implements UpdatesListener {
    // создаем поле логгер для передачи логов в консоль
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final MessageService messageService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, MessageService messageService) {
        this.telegramBot = telegramBot;
        this.messageService = messageService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing update: {}", update);
                String userName = update.message().chat().username();
                String firstName = update.message().chat().firstName();
                long chatId = update.message().chat().id();
                String message = update.message().text();
                if (update.message().text() == null) {
                    logger.warn("Skip message because text is null");
                    return;
                }
                // Process your updates here
                if (message.equals("/start")) {
                    messageService.sendWelcomeMessage(chatId);
                } else if (message.equals("Приют для кошек")) {
                    messageService.sendCatShelterMenu(chatId);
                } else if (message.equals("Приют для собак")) {
                    messageService.sendDogShelterMenu(chatId);
                } else if (message.equals("Узнать информацию о приюте для собак")) {
                    messageService.sendDogShelterInfo(chatId);
                } else if (message.equals("Узнать информацию о приюте для кошек")) {
                    messageService.sendCatShelterInfo(chatId);
                } else if (message.equals("Как взять животное из приюта")) {
                    messageService.sendAnimalAdoptionInstructions(chatId);
                } else if (message.equals("Прислать отчет о питомце")) {
                    messageService.sendPetReport(chatId);

                } else {
                    messageService.sendMessageHelpingVolunteers(chatId, firstName, userName);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    private void changeShelter(Update update, long chatId) {
        if (update.message().text().equals("Узнать информацию о приюте для собак")) {
            messageService.sendDogShelterInfo(chatId);
        } else if (update.message().text().equals("Узнать информацию о приюте для кошек")) {
            messageService.sendCatShelterInfo(chatId);
        }
    }
}
