package com.example.shelterforpets.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
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

}
