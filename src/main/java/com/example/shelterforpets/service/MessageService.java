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

    public void sendShelterInfo(long chatId) {
        String shelterInfoText = "Информация о приюте:\n" +
                "Адрес: ...\n" +
                "Телефон: ...\n" +
                "Email: ...";
        sendNotification(chatId, shelterInfoText);
    }

    public void sendAnimalAdoptionInstructions(long chatId) {
        String adoptionInstructionsText = "Как взять животное из приюта:\n" +
                "1. Заполните анкету на сайте приюта.\n" +
                "2. Пройдите собеседование с сотрудниками приюта.\n" +
                "3. Заключите договор о передаче животного.\n" +
                "4. Оплатите взнос.";
        sendNotification(chatId, adoptionInstructionsText);
    }

    public void sendPetReport(long chatId) {
        String petReportText = "Прислать отчет о питомце:\n" +
                "Для отправки отчета, пожалуйста, заполните форму на нашем сайте.";
        sendNotification(chatId, petReportText);
    }

    private void sendNotification(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    public void sendCatShelterMenu(long chatId) {
        SendMessage message = new SendMessage(chatId, "Выбери, с каким запросом пришел пользователь");

        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Узнать информацию о приюте",
                "Как взять животное из приюта"},
                new String[]{"Прислать отчет о питомце",
                "Позвать волонтера"})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        message.replyMarkup(replyKeyboardMarkup);

        telegramBot.execute(message);
    }

    public void sendDogShelterMenu(long chatId) {
        SendMessage message = new SendMessage(chatId, "Выбери, с каким запросом пришел пользователь");

        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Узнать информацию о приюте",
                        "Как взять животное из приюта"},
                new String[]{"Прислать отчет о питомце",
                        "Позвать волонтера"})
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
