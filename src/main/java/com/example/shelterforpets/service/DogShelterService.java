package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.DogReport;
import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.repository.ClientRepository;
import com.example.shelterforpets.repository.DogReportsRepository;
import com.example.shelterforpets.repository.DogShelterClientRepository;
import com.example.shelterforpets.repository.VolunteerRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;


@Service
public class DogShelterService {

    private final LocalDateTime date = LocalDate.now().atTime(LocalTime.now().getHour(), LocalTime.now().getMinute());

    private final TelegramBot telegramBot;
    private final ShelterService shelterService;
    private final VolunteerRepository volunteerRepository;
    private final ClientRepository clientRepository;
    private final DogShelterClientRepository dogShelterClientRepository;
    private final DogReportsRepository dogReportsRepository;

    public DogShelterService(TelegramBot telegramBot,
                             ShelterService shelterService,
                             VolunteerRepository volunteerRepository,
                             ClientRepository clientRepository,
                             DogShelterClientRepository dogShelterClientRepository,
                             DogReportsRepository dogReportsRepository) {
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
        this.volunteerRepository = volunteerRepository;
        this.clientRepository = clientRepository;
        this.dogShelterClientRepository = dogShelterClientRepository;
        this.dogReportsRepository = dogReportsRepository;

    }

    public void dogShelterMenu(long chatId) {
        shelterService.sendInfoShelterMenu(chatId);
    }

    public void dogInfoShelterMenu(long chatId) {
        shelterService.infoShelterMenu(chatId);
    }

    public void dogAdoptionInstructions(long chatId) {
        shelterService.clientAdoptionInstructionsForDogShelter(chatId);
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


    public void sendRulesDogShelter(long chatId) {
        String shelterInfoText = "Правила знакомства с животным до того, как забрать его из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        sendNotification(chatId, shelterInfoText);
    }

    public void sendDocumentsDogShelter(long chatId) {
        String shelterInfoText = "Список документов, необходимых для того, чтобы взять животное из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForTransportingDogs(long chatId) {
        String shelterInfoText = "Список рекомендаций по транспортировке животного:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsPuppy(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для щенка:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        //!!!!    //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsAdultDogs(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для взрослого животного:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        //!!!!    //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsAdultDogsWithDisabilities(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для животного с " +
                "ограниченными возможностями (зрение, передвижение):\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        sendNotification(chatId, shelterInfoText);
    }

    public void adviceToADogHandlerOnPrimaryCommunicationWithADog(long chatId) {
        String shelterInfoText = "Советы кинолога по первичному общению с собакой:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        sendNotification(chatId, shelterInfoText);
    }

    public void recommendationOnTheCheckedDogHandler(long chatId) {
        String shelterInfoText = "Рекомендации по проверенным кинологам " +
                "для дальнейшего обращения к ним:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        sendNotification(chatId, shelterInfoText);
    }

    public void reasonsWhyTheyMayRefuseAndNotLetYouTakeTheDog(long chatId) {
        String shelterInfoText = "Список причин, почему могут отказать " +
                "и не дать забрать собаку из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        sendNotification(chatId, shelterInfoText);
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

    public void savePhotoReport(Update update) {
        if (dogReportsRepository.findAllByUserId(update.message().chat().id()) != null &&
                dogReportsRepository.findAllByUserId(update.message().chat().id()).getDateReport().isAfter(date.minusDays(1))
        ) {
            DogReport dogReport = dogReportsRepository.findAllByUserId(update.message().chat().id());
            PhotoSize[] photoSizes = update.message().photo();
            dogReport.setDateReport(date);
            if (photoSizes != null && photoSizes.length > 1) {
                PhotoSize photoSize = photoSizes[photoSizes.length - 1];
                GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
                if (getFileResponse.isOk()) {
                    try {
                        byte[] photo = telegramBot.getFileContent(getFileResponse.file());
                        dogReport.setPhotoPet(photo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            dogReportsRepository.save(dogReport);
            sendNotification(update.message().chat().id(), "Ваш отчет зарегистрирован!");
        } else {
            DogReport dogReport = new DogReport();
            dogReport.setUserId(update.message().chat().id());
            dogReport.setDateReport(date);
            PhotoSize[] photoSizes = update.message().photo();
            dogReport.setDateReport(date);
            if (photoSizes != null && photoSizes.length > 1) {
                PhotoSize photoSize = photoSizes[photoSizes.length - 1];
                GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
                if (getFileResponse.isOk()) {
                    try {
                        byte[] photo = telegramBot.getFileContent(getFileResponse.file());
                        dogReport.setPhotoPet(photo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            dogReportsRepository.save(dogReport);
            sendNotification(update.message().chat().id(), "Осталось отправить лишь отчет по указанной форме! :)");
        }
    }

    public void saveStringReport(Update update) {
        if (dogReportsRepository.findAllByUserId(update.message().chat().id()) != null &&
                dogReportsRepository.findAllByUserId(update.message().chat().id()).getDateReport().isAfter(date.minusDays(1))) {
            DogReport dogReport = dogReportsRepository.findAllByUserId(update.message().chat().id());
            dogReport.setPetReport(update.message().text());
            dogReportsRepository.save(dogReport);
            sendNotification(update.message().chat().id(), "Ваш отчет зарегистрирован!");
        } else {
            DogReport dogReport = new DogReport();
            dogReport.setUserId(update.message().chat().id());
            dogReport.setPetReport(update.message().text());
            dogReport.setDateReport(date);
            dogReportsRepository.save(dogReport);
            sendNotification(update.message().chat().id(), "Осталось отправить лишь фото питомца! :)");
        }
    }
    //
    //
    // Methods for RestController
    //
    //

    /**
     * Find client by id from dog shelter repository
     *
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
     *
     * @param userId The ID of the user ID in repository
     * @return Object DogShelter
     */
    public DogShelterClient postClientFromDogShelter(long userId, DogShelterClient dogShelterClient) {
        if (dogShelterClientRepository.existsAllByUserId(userId) == null) {
            dogShelterClient.setUserId(userId);
            return dogShelterClientRepository.save(dogShelterClient);
        }
        return null;
    }


    /**
     * Edit client by id in dog shelter repository
     *
     * @param dogShelterClient The client from dog shelter
     * @return Object DogShelter
     */
    public DogShelterClient putClientFromDogShelter(long userId, DogShelterClient dogShelterClient) {
        if (dogShelterClientRepository.existsAllByUserId(userId) != null) {
            dogShelterClient.setUserId(userId);
            return dogShelterClientRepository.save(dogShelterClient);
        }
        return null;
    }

    /**
     * Delete client by id in dog shelter repository
     *
     * @param userId The ID of the user ID in repository
     */
    public void deleteClientFromDogShelter(long userId) {
        if (dogShelterClientRepository.existsAllByUserId(userId)) {
            dogShelterClientRepository.deleteById(userId);
        }
    }
}
