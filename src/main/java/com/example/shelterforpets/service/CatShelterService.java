package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.repository.CatShelterClientRepository;
import com.example.shelterforpets.repository.ClientRepository;
import com.example.shelterforpets.repository.VolunteerRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class CatShelterService {
    private final TelegramBot telegramBot;
    private final ShelterService shelterService;
    private final VolunteerRepository volunteerRepository;
    private final ClientRepository clientRepository;
    private final CatShelterClientRepository catShelterClientRepository;

    public CatShelterService(TelegramBot telegramBot,
                             ShelterService shelterService,
                             VolunteerRepository volunteerRepository,
                             ClientRepository clientRepository,
                             CatShelterClientRepository catShelterClientRepository) {
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
        this.volunteerRepository = volunteerRepository;
        this.clientRepository = clientRepository;
        this.catShelterClientRepository = catShelterClientRepository;
    }

    public void catShelterMenu(long chatId) {
        shelterService.sendInfoShelterMenu(chatId);
    }

    public void catInfoShelterMenu(long chatId) {
        shelterService.infoShelterMenu(chatId);
    }

    public void catAdoptionInstructions(long chatId) {
        shelterService.sendAnimalAdoptionInstructions(chatId);
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
        volunteerRepository.findAllByUserId(answer); //Добавил случайный выбор волонтера
        SendMessage messageToVolunteer = new SendMessage(
                volunteerRepository.findAllByUserId(answer).getUserId(),
                "С вами хочет связаться клиент: " +
                        clientRepository.findAllByUserId(chatId).getUserId());
        telegramBot.execute(messageToVolunteer);
        SendMessage message = new SendMessage(chatId,
                "С Вами свяжется волонтер!" +
                        " Вы можете ему позвонить по указаному номеру телефона: " +
                        volunteerRepository.findAllByUserId(answer).getPhoneNumber());
        telegramBot.execute(message);
    }

    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId      The ID of the chat to send the notification to.
     * @param name        The text of the notification message.
     * @param phoneNumber The text of the notification message.
     */
    public void saveCatShelterClientNameAndPhoneNumber(long chatId, String name, String phoneNumber) {
        CatShelterClient client = catShelterClientRepository.findAllByUserId(chatId);
        client.setName(name);
        client.setPhoneNumber(phoneNumber);
        catShelterClientRepository.save(client);
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

    /**
     * Find client by id from cat shelter repository
     * @param userId The ID of the user ID in repository
     * @return Object CatShelter
     */
    public CatShelterClient findClientFromCatShelter(long userId) {
        return catShelterClientRepository
                .findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Клиента нет в базе данных!"));
    }

    /**
     * Post client by id from cat shelter repository
     * @param userId The ID of the user ID in repository
     * @return Object CatShelter
     */
    public CatShelterClient postClientFromCatShelter(long userId, CatShelterClient catShelterClient) {
        if (catShelterClientRepository.existsAllByUserId(userId) == null) {
            catShelterClient.setUserId(userId);
            return catShelterClientRepository.save(catShelterClient);
        } return null;
    }


    /**
     * Edit client by id in cat shelter repository
     * @param catShelterClient The client from cat shelter
     * @return Object CatShelter
     */
    public CatShelterClient putClientFromCatShelter(long userId, CatShelterClient catShelterClient) {
        if (catShelterClientRepository.existsAllByUserId(userId) != null) {
            catShelterClient.setUserId(userId);
            return catShelterClientRepository.save(catShelterClient);
        } return null;
    }

    /**
     * Delete client by id in cat shelter repository
     * @param userId The ID of the user ID in repository
     */
    public void deleteClientFromCatShelter(long userId) {
        if(catShelterClientRepository.existsAllByUserId(userId)) {
            catShelterClientRepository.deleteById(userId);
        }
    }




}
