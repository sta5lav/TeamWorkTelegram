package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.exceptions.ClientNotFoundException;
import com.example.shelterforpets.repository.CatShelterClientRepository;
import com.example.shelterforpets.repository.ClientRepository;
import com.example.shelterforpets.repository.VolunteerRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CatShelterService {
    private final TelegramBot telegramBot;
    private final ShelterService shelterService;
    private final VolunteerRepository volunteerRepository;
    private final ClientRepository clientRepository;
    private final CatShelterClientRepository catShelterClientRepository;
    private final NotificationService notificationService;

    public CatShelterService(TelegramBot telegramBot,
                             ShelterService shelterService,
                             VolunteerRepository volunteerRepository,
                             ClientRepository clientRepository,
                             CatShelterClientRepository catShelterClientRepository,
                             NotificationService notificationService) {
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
        this.volunteerRepository = volunteerRepository;
        this.clientRepository = clientRepository;
        this.catShelterClientRepository = catShelterClientRepository;
        this.notificationService = notificationService;
    }

    public void catShelterMenu(long chatId) {
        shelterService.sendInfoShelterMenu(chatId);
    }

    public void catInfoShelterMenu(long chatId) {
        shelterService.infoShelterMenu(chatId);
    }

    public void catAdoptionInstructions(long chatId) {
        shelterService.clientAdoptionInstructionsForCatShelter(chatId);
    }

    public void report(long chatId) {
        shelterService.sendPetReport(chatId);
    }

    public void messageHelpingVolunteers(long chatId, String firstName, String userName) {
        shelterService.sendMessageHelpingVolunteers(chatId, firstName, userName);
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
        notificationService.sendNotification(chatId, shelterInfoText);
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
        notificationService.sendNotification(chatId, scheduleCatShelter);
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
        notificationService.sendNotification(chatId, securityContactDetailsCatShelter);
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
        notificationService.sendNotification(chatId, recommendationInTheCatShelter);
    }

    public void nameAndPhoneNumberPattern(long chatId) {
        shelterService.sendNameAndPhoneNumberPattern(chatId);
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
        volunteerRepository.findByUserId(answer); //Добавил случайный выбор волонтера

        long volunteerId = volunteerRepository.findByUserId(answer).orElseThrow(() ->
                new RuntimeException("Волонтера нет в базе данных!")).getUserId();
        String messageToVolunteer = "С вами хочет связаться клиент: " +
                clientRepository.findByUserId(chatId).getUserId();
        notificationService.sendNotification(volunteerId, messageToVolunteer);

        String messageToClient = "С Вами свяжется волонтер!" +
                " Вы можете ему позвонить по указаному номеру телефона: " +
                volunteerRepository.findByUserId(answer).orElseThrow(() ->
                        new ClientNotFoundException("Клиента нет в базе данных!")).getPhoneNumber();
        notificationService.sendNotification(chatId, messageToClient);
    }

    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId      The ID of the chat to send the notification to.
     * @param name        The text of the notification message.
     * @param phoneNumber The text of the notification message.
     */
    public void saveCatShelterClientNameAndPhoneNumber(long chatId, String name, String phoneNumber) {
        CatShelterClient client = catShelterClientRepository.findByUserId(chatId).
                orElseThrow(() -> new ClientNotFoundException("Клиента нет в базе данных!"));
        client.setName(name);
        client.setPhoneNumber(phoneNumber);
        catShelterClientRepository.save(client);
    }


    public void sendRulesCatShelter(long chatId) {
        String shelterInfoText = "Правила знакомства с животным до того, как забрать его из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ КОШЕК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void sendDocumentsCatShelter(long chatId) {
        String shelterInfoText = "Список документов, необходимых для того, чтобы взять животное из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ КОШЕК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForTransportingCats(long chatId) {
        String shelterInfoText = "Список рекомендаций по транспортировке животного:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ КОШЕК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsKitten(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для котенка:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        //!!!!    //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ КОШЕК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsAdultCats(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для взрослого животного:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        //!!!!    //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ КОШЕК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsAdultCatsWithDisabilities(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для животного с " +
                "ограниченными возможностями (зрение, передвижение):\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ КОШЕК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    //
    //
    // Methods for RestController
    //
    //
    /**
     * Find client by id from cat shelter repository
     *
     * @param userId The ID of the user ID in repository
     * @return Object CatShelter
     */
    public CatShelterClient findClientFromCatShelter(long userId) {
        return catShelterClientRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ClientNotFoundException("Клиента нет в базе данных!"));
    }

    /**
     * Post client by id from cat shelter repository
     *
     * @param userId The ID of the user ID in repository
     * @return Object CatShelter
     */
    public CatShelterClient postClientFromCatShelter(long userId, CatShelterClient catShelterClient) {
        if (!catShelterClientRepository.existsByUserId(userId)) {
            catShelterClient.setUserId(userId);
            return catShelterClientRepository.save(catShelterClient);
        }
        return null;
    }


    /**
     * Edit client by id in cat shelter repository
     *
     * @param catShelterClient The client from cat shelter
     * @return Object CatShelter
     */
    public CatShelterClient putClientFromCatShelter(long userId, CatShelterClient catShelterClient) {
        if (catShelterClientRepository.existsByUserId(userId)) {
            catShelterClient.setUserId(userId);
            return catShelterClientRepository.save(catShelterClient);
        }
        return null;
    }

    /**
     * Delete client by id in cat shelter repository
     *
     * @param userId The ID of the user ID in repository
     */
    public void deleteClientFromCatShelter(long userId) {
        if (catShelterClientRepository.existsByUserId(userId)) {
            catShelterClientRepository.deleteById(userId);
        }
    }
}
