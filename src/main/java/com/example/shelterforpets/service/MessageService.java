package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.listener.TelegramBotUpdatesListener;
import com.example.shelterforpets.repository.CatReportsRepository;
import com.example.shelterforpets.repository.CatShelterClientRepository;
import com.example.shelterforpets.repository.DogReportsRepository;
import com.example.shelterforpets.repository.DogShelterClientRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    // создаем поле для передачи логов в консоль
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    //инжектим класс телеграм бота из библиотеки
    private TelegramBot telegramBot;

    // Инжектим репозиторий для клиентов приюта для кошек
    @Autowired
    CatShelterClientRepository catShelterClientRepository;

    // Инжектим репозиторий для клиентов приюта для собак
    @Autowired
    DogShelterClientRepository dogShelterClientRepository;

    // Инжектим репозиторий для отчетов клиентов приюта для кошек
    @Autowired
    CatReportsRepository catReportsRepository;

    // Инжектим репозиторий для отчетов клиентов приюта для собак
    @Autowired
    DogReportsRepository dogReportsRepository;


    // Конструктор для телеграмбота
    public MessageService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    //создаем метод для приветственного сообщения
    public void sendWelcomeMessage(long chatId) {
        String welcomeText = "Привет! Я бот, и я готов помочь тебе.";
        sendShelterMenu(chatId, welcomeText);
    }

    //создаем метод для выбора приюта для клиентов
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

    //создаем метод, который не смог определить запрос пользователя и через лог вызываем волонтера
    public void sendMessageHelpingVolunteers(long chatId, String firstName, String userName) {
        String helpingVolunteers =
                "К сожалению, я не могу найти ответ на Ваш запрос. Бегу за волонтёром! " +
                        "Не волнуйтесь, с Вами свяжутся в ближайшее время!";
        SendMessage message = new SendMessage(chatId, helpingVolunteers);
        telegramBot.execute(message);
        logger.warn(firstName + " (" + userName + ")" + " просит Вас связаться с ним!");
    }

}
