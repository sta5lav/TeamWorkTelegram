package com.example.shelterforpets.service;

import com.example.shelterforpets.listener.TelegramBotUpdatesListener;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private TelegramBot telegramBot;

    public MessageService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void sendWelcomeMessage(long chatId) {
        String welcomeText = "Привет! Я бот, и я готов помочь тебе.";
        sendShelterMenu(chatId, welcomeText);
    }

    private void sendShelterMenu(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);

        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                "Приют для собак", "Приют для кошек")
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        message.replyMarkup(replyKeyboardMarkup);

        telegramBot.execute(message);
    }

    public void sendMessageHelpingVolunteers(long chatId, String firstName, String userName) {
        String helpingVolunteers =
                "К сожалению, я не могу найти ответ на Ваш запрос. Бегу за волонтёром! Не волнуйтесь, с Вами свяжутся в ближайшее время!";
        SendMessage message = new SendMessage(chatId, helpingVolunteers);
        telegramBot.execute(message);
        logger.warn(firstName + " (" + userName + ")" + " просит Вас связаться с ним!");
    }

}
