package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.listener.TelegramBotUpdatesListener;
import com.example.shelterforpets.repository.CatReportsRepository;
import com.example.shelterforpets.repository.CatShelterClientRepository;
import com.example.shelterforpets.repository.DogReportsRepository;
import com.example.shelterforpets.repository.DogShelterClientRepository;
import com.example.shelterforpets.service.firstStage.FirstStageService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    // создаем поле для передачи логов в консоль
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    // Инжектим класс телеграм бота из библиотеки
    private TelegramBot telegramBot;

    private FirstStageService firstStageService;

    // Инжектим репозиторий для клиентов приюта для кошек
    private CatShelterClientRepository catShelterClientRepository;

    // Инжектим репозиторий для клиентов приюта для собак
    private DogShelterClientRepository dogShelterClientRepository;

    // Инжектим репозиторий для отчетов клиентов приюта для кошек
    private CatReportsRepository catReportsRepository;

    // Инжектим репозиторий для отчетов клиентов приюта для собак
    private DogReportsRepository dogReportsRepository;


    // Конструктор для телеграмбота и репозиториев
    public MessageService(TelegramBot telegramBot, CatShelterClientRepository catShelterClientRepository,
                          DogShelterClientRepository dogShelterClientRepository,
                          CatReportsRepository catReportsRepository,
                          DogReportsRepository dogReportsRepository,
                          FirstStageService firstStageService) {
        this.telegramBot = telegramBot;
        this.catShelterClientRepository = catShelterClientRepository;
        this.dogShelterClientRepository = dogShelterClientRepository;
        this.catReportsRepository = catReportsRepository;
        this.dogReportsRepository = dogReportsRepository;
        this.firstStageService = firstStageService;
    }

    //создаем метод для приветственного сообщения с выбором приюта

    /**
     * Sends a welcome message to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void sendWelcomeMessage(long chatId) {
        String welcomeText = "Привет! Я бот, и я готов помочь Вам. Какой приют Вас интересует?";
        sendShelterMenu(chatId, welcomeText);
    }

    //создаем метод для выбора приюта для клиентов

    /**
     * Sends a shelter menu message with the specified text to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     * @param text The text of the menu message.
     */
    public void sendShelterMenu(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);

        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                "Приют для собак", "Приют для кошек")
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        message.replyMarkup(replyKeyboardMarkup);
        telegramBot.execute(message);
    }

    /**
     * Sends a cat shelter menu message to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void sendInfoShelterMenu(long chatId) {
        SendMessage message = new SendMessage(chatId, "Выбери, с каким запросом пришел пользователь:");
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Узнать информацию о приюте",
                        "Как взять животное из приюта"},
                new String[]{"Прислать отчет о питомце",
                        "Позвать волонтера"})
                .oneTimeKeyboard(false)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        message.replyMarkup(replyKeyboardMarkup);
        telegramBot.execute(message);
    }


    /**
     * Sends animal adoption instructions to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the instructions to.
     */
    public void sendAnimalAdoptionInstructions(long chatId) {
        String adoptionInstructionsText = "Как взять животное из приюта:\n" +
                "1. Заполните анкету на сайте приюта.\n" +
                "2. Пройдите собеседование с сотрудниками приюта.\n" +
                "3. Заключите договор о передаче животного.\n" +
                "4. Оплатите взнос.";
        sendNotification(chatId, adoptionInstructionsText);
    }

    /**
     * Sends a pet report notification to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the pet report notification to.
     */
    public void sendPetReport(long chatId) {
        String petReportText = "Прислать отчет о питомце:\n" +
                "Для отправки отчета, пожалуйста, заполните форму на нашем сайте.";
        sendNotification(chatId, petReportText);
    }

    //создаем метод, который не смог определить запрос пользователя и через лог вызываем волонтера
    /**
     * Sends a message to the chat ID indicating that a volunteer will assist the user and provides a shelter menu.
     * It also logs a warning message with the user's first name and username.
     *
     * @param chatId    The ID of the chat to send the message to.
     * @param firstName The first name of the user.
     * @param userName  The username of the user.
     */
    public void sendMessageHelpingVolunteers(long chatId, String firstName, String userName) {
        String helpingVolunteers =
                "К сожалению, я не могу найти ответ на Ваш запрос. Бегу за волонтёром! " +
                        "Не волнуйтесь, с Вами свяжутся в ближайшее время! ";
        SendMessage message = new SendMessage(chatId, helpingVolunteers);
        telegramBot.execute(message);
        sendShelterMenu(chatId, "Какой приют Вас интересует?");
        logger.warn(firstName + " (" + userName + ")" + " просит Вас связаться с ним!");
    }

    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId The ID of the chat to send the notification to.
     * @param text   The text of the notification message.
     */
    private void sendNotification(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

}
