package com.example.shelterforpets.service.firstStage;


import com.example.shelterforpets.entity.Client;
import com.example.shelterforpets.listener.TelegramBotUpdatesListener;
import com.example.shelterforpets.repository.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class FirstStageService {

    // создаем поле для передачи логов в консоль
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    // Инжектим класс телеграм бота из библиотеки
    private TelegramBot telegramBot;

    // Инжектим репозитории для клиентов
    private ClientRepository clientRepository;

    private VolunteerRepository volunteerRepository;



    // Конструктор для телеграмбота и репозиториев


    public FirstStageService(TelegramBot telegramBot,
                             ClientRepository clientRepository,
                             VolunteerRepository volunteerRepository) {
        this.telegramBot = telegramBot;
        this.clientRepository = clientRepository;
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Sends a dog shelter menu message to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void infoShelterMenu(long chatId) {
        SendMessage message = new SendMessage(chatId, "Выбери, с каким запросом пришел пользователь");
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Рассказать о приюте"},
                new String[]{"Выдать расписание работы приюта и адрес, схему проезда"},
                new String[]{"Выдать контактные данные охраны для оформления пропуска на машину"},
                new String[]{"Выдать общие рекомендации о технике безопасности на территории приюта"},
                new String[]{"Принять и записать контактные данные для связи"},
                new String[]{"Позвать волонтера"})
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        message.replyMarkup(replyKeyboardMarkup);
        telegramBot.execute(message);

    }

    //Информация о приюте для кошек

    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void sendCatShelterInfo(long chatId) {
        String shelterInfoText = "Информация о приюте:\n" +
                "Адрес: ...\n" +
                "Телефон: ...\n" +
                "Email: ...";
        //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ КОШЕК
        sendNotification(chatId, shelterInfoText);
    }

    //Информация о приюте для собак

    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void sendDogShelterInfo(long chatId) {
        String shelterInfoText = "Информация о приюте:\n" +
                "Адрес: ...\n" +
                "Телефон: ...\n" +
                "Email: ...";
        //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        sendNotification(chatId, shelterInfoText);
    }


    //Вывод расписания приюта для кошек

    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void scheduleCatShelter(long chatId) {
        String scheduleCatShelter = "Информация о работе приюта:\n" +
                "Время работы: ...\n" +
                "Адрес: ...\n" +
                "Схема проезда: ...";
        //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О РАБОТЕ ПРИЮТА ДЛЯ КОШЕК
        sendNotification(chatId, scheduleCatShelter);
    }

    //Вывод расписания приюта для собак
    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void scheduleDogShelter(long chatId) {
        String scheduleDogShelter = "Информация о работе приюта:\n" +
                "Время работы: ...\n" +
                "Адрес: ...\n" +
                "Схема проезда: ...";
        //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О РАБОТЕ ПРИЮТА ДЛЯ СОБАК
        sendNotification(chatId, scheduleDogShelter);

    }

    //Вывод контактных данных охраны для оформления пропуска на машину в приют для кошек
    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void securityContactDetailsCatShelter(long chatId) {
        String securityContactDetailsCatShelter = "Контактные данные охраны приюта:\n" +
                "Телефон: ...\n" +
                "Имя: ...";
        //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ ОБ ОХРАНЕ ПРИЮТА ДЛЯ КОШЕК
        sendNotification(chatId, securityContactDetailsCatShelter);


    }

    //Вывод контактных данных охраны для оформления пропуска на машину в приют для собак
    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void securityContactDetailsDogShelter(long chatId) {
        String securityContactDetailsDogShelter = "Контактные данные охраны приюта:\n" +
                "Телефон: ...\n" +
                "Имя: ...";
        //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ ОБ ОХРАНЕ ПРИЮТА ДЛЯ СОБАК
        sendNotification(chatId, securityContactDetailsDogShelter);
    }

    //Вывод общих рекомендаций о технике безопасности на территории приюта для кошек
    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void recommendationInTheCatShelter(long chatId) {
        String recommendationInTheCatShelter = "Техника безопасности:\n" +
                "1. ...\n" +
                "2. ...";
        //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ТБ В ПРИЮТЕ ДЛЯ КОШЕК
        sendNotification(chatId, recommendationInTheCatShelter);
    }

    //Вывод общих рекомендаций о технике безопасности на территории приюта для собак
    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void recommendationInTheDogShelter(long chatId) {
        String recommendationInTheCatShelter = "Техника безопасности:\n" +
                "1. ...\n" +
                "2. ...";
        //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ТБ В ПРИЮТЕ ДЛЯ СОБАК
        sendNotification(chatId, recommendationInTheCatShelter);
    }

    //Сохранение контактных данных для связи с клиентом
    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void saveClientInCatShelter(long chatId) {
        String saveClient = "Введите контактные данные в формате:\n" +
                "89991234567 ИМЯ";
        sendNotification(chatId, saveClient);
    }

    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void saveClientInDogShelter(long chatId) {
        String saveClient = "Введите контактные данные в формате:\n" +
                "89991234567 ИМЯ";
        sendNotification(chatId, saveClient);
    }

    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId      The ID of the chat to send the notification to.
     * @param name        The text of the notification message.
     * @param phoneNumber The text of the notification message.
     */
    public void saveCatShelterClientNameAndPhoneNumber(long chatId, String name, String phoneNumber) {
        Client client = clientRepository.findAllByUserId(chatId);
        client.setName(name);
        client.setPhoneNumber(phoneNumber);
        clientRepository.save(client);
    }

    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId      The ID of the chat to send the notification to.
     * @param name        The text of the notification message.
     * @param phoneNumber The text of the notification message.
     */
    public void saveDogShelterClientNameAndPhoneNumber(long chatId, String name, String phoneNumber) {
        Client client = clientRepository.findAllByUserId(chatId);
        client.setName(name);
        client.setPhoneNumber(phoneNumber);
        clientRepository.save(client);
    }


    //Позвать волонтера приюта для кошек
    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void getHelpingCatShelterVolunteers(long chatId) {
        Random rn = new Random();
        int answer = rn.nextInt(5) + 1;
        volunteerRepository.findAllById(answer); //Добавил случайный выбор волонтера
        SendMessage messageToVolunteer = new SendMessage(
                volunteerRepository.findAllById(answer).getUserId(),
                "С вами хочет связаться клиент: " +
                        clientRepository.findAllByUserId(chatId).getUserId());
        telegramBot.execute(messageToVolunteer);
        SendMessage message = new SendMessage(chatId,
                "С Вами свяжется волонтер!" +
                        " Вы можете ему позвонить по указаному номеру телефона: " +
                        volunteerRepository.findAllById(answer).getPhoneNumber());
        telegramBot.execute(message);

    }

    //Позвать волонтера приюта для собак
    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void getHelpingDogShelterVolunteers(long chatId) {
        Random rn = new Random();
        int answer = rn.nextInt(5) + 1;
        volunteerRepository.findAllById(answer); //Добавил случайный выбор волонтера
        SendMessage messageToVolunteer = new SendMessage(
                volunteerRepository.findAllById(answer).getUserId(),
                "С вами хочет связаться клиент: " +
                        clientRepository.findAllByUserId(chatId).getUserId());
        telegramBot.execute(messageToVolunteer);
        SendMessage message = new SendMessage(chatId,
                "С Вами свяжется волонтер!" +
                        " Вы можете ему позвонить по указаному номеру телефона: " +
                        volunteerRepository.findAllById(answer).getPhoneNumber());
        telegramBot.execute(message);

    }

    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId The ID of the chat to send the notification to.
     * @param text   The text of the notification message.
     */
    private void sendNotification(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                "Вернуться в меню информации о приюте")
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        message.replyMarkup(replyKeyboardMarkup);
        telegramBot.execute(message);
    }

    //сохранение клиента в базу данных
    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId      The ID of the chat to send the notification to.
     * @param menuShelter The text of the notification message.
     */
    public void saveClient(long chatId, String menuShelter) {
        if (!clientRepository.existsAllByUserId(chatId)) {
            Client client = new Client();
            client.setUserId(chatId);
            client.setStep(menuShelter);
            clientRepository.save(client);
        } else {
            clientRepository.findAllByUserId(chatId).setStep(menuShelter);
        }
    }

    //получить данные к какому меню приюта обратился пользователь
    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId      The ID of the chat to send the notification to.
     */
    public String getStepClient(long chatId) {
        return clientRepository.findAllByUserId(chatId).getStep();
    }


}
