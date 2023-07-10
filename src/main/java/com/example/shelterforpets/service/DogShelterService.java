package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.repository.ClientRepository;
import com.example.shelterforpets.repository.DogShelterClientRepository;
import com.example.shelterforpets.repository.VolunteerRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DogShelterService {

    private final TelegramBot telegramBot;
    private final ShelterService shelterService;
    private final VolunteerRepository volunteerRepository;
    private final ClientRepository clientRepository;
    private final DogShelterClientRepository dogShelterClientRepository;

    public DogShelterService(TelegramBot telegramBot,
                             ShelterService shelterService,
                             VolunteerRepository volunteerRepository,
                             ClientRepository clientRepository,
                             DogShelterClientRepository dogShelterClientRepository) {
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
        this.volunteerRepository = volunteerRepository;
        this.clientRepository = clientRepository;
        this.dogShelterClientRepository = dogShelterClientRepository;
    }

    public void dogShelterMenu(long chatId) {
        shelterService.sendInfoShelterMenu(chatId);
    }

    public void dogInfoShelterMenu(long chatId) {
        shelterService.infoShelterMenu(chatId);
    }

    public void dogAdoptionInstructions(long chatId) {
        shelterService.sendAnimalAdoptionInstructions(chatId);
    }

    public void report(long chatId) {
        shelterService.sendPetReport(chatId);
    }

    public void messageHelpingVolunteers(long chatId, String firstName, String userName) {
        shelterService.sendMessageHelpingVolunteers(chatId, firstName, userName);
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

    public void nameAndPhoneNumberPattern(long chatId) {
        shelterService.sendNameAndPhoneNumberPattern(chatId);
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
    public void saveDogShelterClientNameAndPhoneNumber(long chatId, String name, String phoneNumber) {
        DogShelterClient client = dogShelterClientRepository.findAllByUserId(chatId);
        client.setName(name);
        client.setPhoneNumber(phoneNumber);
        dogShelterClientRepository.save(client);
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
     * Find client by id from dog shelter repository
     * @param userId The ID of the user ID in repository
     * @return Object DogShelter
     */
    public DogShelterClient findClientFromDogShelter(long userId) {
        return dogShelterClientRepository
                .findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Клиента нет в базе данных!"));
    }

    /**
     * Post client by id from dog shelter repository
     * @param userId The ID of the user ID in repository
     * @return Object DogShelter
     */
    public DogShelterClient postClientFromDogShelter(long userId, DogShelterClient dogShelterClient) {
        if (dogShelterClientRepository.existsAllByUserId(userId) == null) {
            dogShelterClient.setUserId(userId);
            return dogShelterClientRepository.save(dogShelterClient);
        } return null;
    }


    /**
     * Edit client by id in dog shelter repository
     * @param dogShelterClient The client from dog shelter
     * @return Object DogShelter
     */
    public DogShelterClient putClientFromDogShelter(long userId, DogShelterClient dogShelterClient) {
        if (dogShelterClientRepository.existsAllByUserId(userId) != null) {
            dogShelterClient.setUserId(userId);
            return dogShelterClientRepository.save(dogShelterClient);
        } return null;
    }

    /**
     * Delete client by id in dog shelter repository
     * @param userId The ID of the user ID in repository
     */
    public void deleteClientFromDogShelter(long userId) {
        if(dogShelterClientRepository.existsAllByUserId(userId)) {
            dogShelterClientRepository.deleteById(userId);
        }
    }

}
