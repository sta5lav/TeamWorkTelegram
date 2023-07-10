package com.example.shelterforpets.service;


import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.entity.Client;
import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.entity.Step;
import com.example.shelterforpets.listener.TelegramBotUpdatesListener;
import com.example.shelterforpets.repository.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ShelterService {

    // создаем поле для передачи логов в консоль
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    // Инжектим класс телеграм бота из библиотеки
    private TelegramBot telegramBot;

    // Инжектим репозитории для клиентов
    private ClientRepository clientRepository;
    private CatShelterClientRepository catShelterClientRepository;
    private DogShelterClientRepository dogShelterClientRepository;

    // Конструктор для телеграмбота и репозиториев
    public ShelterService(TelegramBot telegramBot,
                          ClientRepository clientRepository,
                          VolunteerRepository volunteerRepository,
                          CatShelterClientRepository catShelterClientRepository,
                          DogShelterClientRepository dogShelterClientRepository) {
        this.telegramBot = telegramBot;
        this.clientRepository = clientRepository;
        this.catShelterClientRepository = catShelterClientRepository;
        this.dogShelterClientRepository = dogShelterClientRepository;
    }

    /**
     * Sends a dog shelter menu message to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void infoShelterMenu(long chatId) {
        SendMessage message = new SendMessage(chatId, "Что вас интересует?");
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"О приюте"},
                new String[]{"Расписание работы приюта и адрес, схема проезда"},
                new String[]{"Контактные данные охраны для оформления пропуска на машину"},
                new String[]{"Общие рекомендации о технике безопасности на территории приюта"},
                new String[]{"Принять и записать контактные данные для связи"},
                new String[]{"Позвать волонтера"},
                new String[]{"Вернуться в меню приюта"})
                .oneTimeKeyboard(false)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        message.replyMarkup(replyKeyboardMarkup);
        telegramBot.execute(message);

    }

    //Сохранение контактных данных для связи с клиентом
    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void saveClientInCatShelter(long chatId) {
        if (!catShelterClientRepository.existsAllByUserId(chatId)) {
            CatShelterClient client = new CatShelterClient();
            client.setUserId(chatId);
            catShelterClientRepository.save(client);
        }
    }

    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void saveClientInDogShelter(long chatId) {
        if (!dogShelterClientRepository.existsAllByUserId(chatId)) {
            DogShelterClient client = new DogShelterClient();
            client.setUserId(chatId);
            dogShelterClientRepository.save(client);
        }
    }

    public void sendNameAndPhoneNumberPattern(long chatId) {
        String saveClient = "Введите контактные данные в формате:\n" +
                "89991234567 ИМЯ";
        sendNotification(chatId, saveClient);
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

    //сохранение клиента в базу данных
    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId      The ID of the chat to send the notification to.
     * @param step The text of the notification message.
     */
    public void saveClient(long chatId, Step step) {
        if (!clientRepository.existsAllByUserId(chatId)) {
            Client client = new Client();
            client.setUserId(chatId);
            client.setStep(step);
            clientRepository.save(client);
        } else {
            Client client = clientRepository.findAllByUserId(chatId);
            client.setStep(step);
            clientRepository.save(client);
        }
    }

    //получить данные к какому меню приюта обратился пользователь
    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId      The ID of the chat to send the notification to.
     */
    public Step getStepClient(long chatId) {
        return clientRepository.findAllByUserId(chatId).getStep();
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
        SendMessage message = new SendMessage(chatId, "Что вас интересует?");
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
}
