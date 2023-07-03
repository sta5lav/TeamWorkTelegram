package com.example.shelterforpets.listener;

import com.example.shelterforpets.service.MessageService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
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
                if (update.message().text() == null) {
                    logger.warn("Skip message because text is null");
                    return;
                }
                // Process your updates here
                if (update.message().text().equals("/start")) {
                    long chatId = update.message().chat().id();
                    messageService.sendWelcomeMessage(chatId);
                } else if (update.message().text().equals("Приют для собак")) {
                    long chatId = update.message().chat().id();
                    messageService.sendDogShelterMenu(chatId);
                } else if (update.message().text().equals("Приют для кошек")) {
                    long chatId = update.message().chat().id();
                    messageService.sendCatShelterMenu(chatId);
                } else if (update.message().text().equals("Узнать информацию о приюте")) {
                    long chatId = update.message().chat().id();
                    messageService.sendShelterInfo(chatId);
                } else if (update.message().text().equals("Как взять животное из приюта")) {
                    long chatId = update.message().chat().id();
                    messageService.sendAnimalAdoptionInstructions(chatId);
                } else if (update.message().text().equals("Прислать отчет о питомце")) {
                    long chatId = update.message().chat().id();
                    messageService.sendPetReport(chatId);
                } else {
                    messageService.sendMessageHelpingVolunteers(update.message().chat().id(), firstName, userName);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
