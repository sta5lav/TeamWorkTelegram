package com.example.shelterforpets.listener;

import com.example.shelterforpets.service.StepService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Pattern;

@Service

public class TelegramBotUpdatesListener implements UpdatesListener {
    // создаем поле логгер для передачи логов в консоль
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private final TelegramBot telegramBot;
    private final StepService stepService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, StepService stepService) {
        this.telegramBot = telegramBot;
        this.stepService = stepService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Processes the updates received from the Telegram Bot API.
     *
     * @param updates The list of updates to process.
     * @return The status of the updates processing.
     */
    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(stepService::process);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
