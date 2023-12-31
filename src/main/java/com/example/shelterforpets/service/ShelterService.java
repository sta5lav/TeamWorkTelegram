package com.example.shelterforpets.service;


import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.entity.Client;
import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.constants.Step;
import com.example.shelterforpets.listener.TelegramBotUpdatesListener;
import com.example.shelterforpets.repository.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.example.shelterforpets.constants.Constants.*;


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
    private NotificationService notificationService;

    // Конструктор для телеграмбота и репозиториев
    public ShelterService(TelegramBot telegramBot,
                          ClientRepository clientRepository,
                          CatShelterClientRepository catShelterClientRepository,
                          DogShelterClientRepository dogShelterClientRepository,
                          NotificationService notificationService) {
        this.telegramBot = telegramBot;
        this.clientRepository = clientRepository;
        this.catShelterClientRepository = catShelterClientRepository;
        this.dogShelterClientRepository = dogShelterClientRepository;
        this.notificationService = notificationService;
    }

    /**
     * Sends a dog shelter menu message to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void infoShelterMenu(long chatId) {
        String text = "Что вас интересует?";
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{ABOUT_SHELTER},
                new String[]{SCHEDULE_OF_SHELTER},
                new String[]{CONTACTS_FOR_ACCESS},
                new String[]{RECOMMENDATION_OF_SAFETY},
                new String[]{RECORD_CONTACT_DETAILS_FOR_COMMUNICATION},
                new String[]{CALL_A_VOLUNTEER},
                new String[]{BACK_TO_MENU_SHELTER})
                .oneTimeKeyboard(false)   // optional
                .resizeKeyboard(false)    // optional
                .selective(true);        // optional
        notificationService.sendNotificationWithMenu(chatId, text, replyKeyboardMarkup);
    }

    public void clientAdoptionInstructionsForCatShelter(long chatId) {
        String text = "Выберите пункт меню.";
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{RULES_FOR_GETTING_TO_KNOW_AN_ANIMAL},
                new String[]{A_LIST_OF_DOCUMENTS_REQUIRED_TO_TAKE_AN_ANIMAL},
                new String[]{LIST_OF_RECOMMENDATIONS_FOR_TRANSPORTING_AN_ANIMAL},
                new String[]{HOME_IMPROVEMENT_FOR_A_KITTEN},
                new String[]{HOME_IMPROVEMENT_FOR_AN_ADULT_ANIMAL},
                new String[]{HOME_IMPROVEMENT_FOR_AN_ANIMAL_WITH_DISABILITIES},
                new String[]{RECORD_CONTACT_DETAILS_FOR_COMMUNICATION},
                new String[]{CALL_A_VOLUNTEER},
                new String[]{BACK_TO_MENU_SHELTER})
                .oneTimeKeyboard(false)   // optional
                .resizeKeyboard(false)    // optional
                .selective(true);        // optional
        notificationService.sendNotificationWithMenu(chatId, text, replyKeyboardMarkup);
    }

    public void clientAdoptionInstructionsForDogShelter(long chatId) {
        String text = "Выберите пункт меню.";
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{RULES_FOR_GETTING_TO_KNOW_AN_ANIMAL},
                new String[]{A_LIST_OF_DOCUMENTS_REQUIRED_TO_TAKE_AN_ANIMAL},
                new String[]{LIST_OF_RECOMMENDATIONS_FOR_TRANSPORTING_AN_ANIMAL},
                new String[]{HOME_IMPROVEMENT_FOR_A_PUPPY},
                new String[]{HOME_IMPROVEMENT_FOR_AN_ADULT_ANIMAL},
                new String[]{HOME_IMPROVEMENT_FOR_AN_ANIMAL_WITH_DISABILITIES},
                new String[]{GIVE_ADVICE_TO_A_DOG_HANDLER_ON_PRIMARY_COMMUNICATION_WITH_A_DOG},
                new String[]{GIVE_RECOMMENDATIONS_ON_THE_CHECKED_DOG_HANDLERS},
                new String[]{REASONS_WHY_THEY_MAY_REFUSE_AND_NOT_LET_YOU_TAKE_THE_DOG},
                new String[]{RECORD_CONTACT_DETAILS_FOR_COMMUNICATION},
                new String[]{CALL_A_VOLUNTEER}, new String[]{BACK_TO_MENU_SHELTER})
                .oneTimeKeyboard(false)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        notificationService.sendNotificationWithMenu(chatId, text, replyKeyboardMarkup);
    }

    public void backToMenuShelter(long chatId, String text) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                BACK_TO_MENU_SHELTER)
                .oneTimeKeyboard(false)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        SendMessage message = new SendMessage(chatId, text);
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
        if (!catShelterClientRepository.existsByUserId(chatId)) {
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
        if (!dogShelterClientRepository.existsByUserId(chatId)) {
            DogShelterClient client = new DogShelterClient();
            client.setUserId(chatId);
            dogShelterClientRepository.save(client);
        }
    }

    public void sendNameAndPhoneNumberPattern(long chatId) {
        String saveClient = "Введите контактные данные в формате:\n" +
                "89991234567 ИМЯ";
        notificationService.sendNotification(chatId, saveClient);
    }

    //сохранение клиента в базу данных

    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId The ID of the chat to send the notification to.
     * @param step   The text of the notification message.
     */
    public void saveClient(long chatId, Step step) {
        if (!clientRepository.existsByUserId(chatId)) {
            Client client = new Client();
            client.setUserId(chatId);
            client.setStep(step);
            clientRepository.save(client);
        } else {
            Client client = clientRepository.findByUserId(chatId);
            client.setStep(step);
            clientRepository.save(client);
        }
    }

    //получить данные к какому меню приюта обратился пользователь

    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId The ID of the chat to send the notification to.
     */
    public Step getStepClient(long chatId) {
        return clientRepository.findByUserId(chatId).getStep();
    }

    //создаем метод для выбора приюта для клиентов

    /**
     * Sends a shelter menu message with the specified text to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     * @param text   The text of the menu message.
     */
    public void sendShelterMenu(long chatId, String text) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                CAT_SHELTER, DOG_SHELTER)
                .oneTimeKeyboard(true)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        notificationService.sendNotificationWithMenu(chatId, text, replyKeyboardMarkup);
    }

    /**
     * Sends a cat shelter menu message to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void sendInfoShelterMenu(long chatId) {
        String text = "Что вас интересует?";
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{FOUND_INFO_ABOUT_SHELTER,
                        HOW_TAKE_A_ANIMAL},
                new String[]{SEND_A_PET_REPORT,
                        CALL_A_VOLUNTEER})
                .oneTimeKeyboard(false)   // optional
                .resizeKeyboard(true)    // optional
                .selective(true);        // optional
        notificationService.sendNotificationWithMenu(chatId, text, replyKeyboardMarkup);
    }

    /**
     * Sends a pet report notification to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the pet report notification to.
     */
    public void sendPetReport(long chatId) {
        String petReportText = "Прислать отчет необходимо по следующей форме: \n" +
                "1. Фотография питомца; \n" +
                "2. В одном сообщении указать рацион животного, общее самочувствие " +
                "и привыкание к новому месту и необходимо указать изменения в поведении:" +
                " отказ от старых привычек, приобретение новых. \n" +
                "После того, как Вы отправите отчет, волонтеры его проверят и свящутся с Вами, " +
                "если будут замечания";
        notificationService.sendNotification(chatId, petReportText);
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
        notificationService.sendNotification(chatId, helpingVolunteers);
        sendShelterMenu(chatId, "Какой приют Вас интересует?");
        logger.warn(firstName + " (" + userName + ")" + " просит Вас связаться с ним!");
    }
}
