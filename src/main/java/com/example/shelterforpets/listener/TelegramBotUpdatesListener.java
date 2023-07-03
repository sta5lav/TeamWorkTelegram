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




                } else {
                    messageService.sendMessageHelpingVolunteers(update.message().chat().id(), firstName, userName);   // if an unknown request get helping volunteers
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
