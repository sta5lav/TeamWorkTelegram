package com.example.shelterforpets.service;

import com.example.shelterforpets.constants.Status;
import com.example.shelterforpets.entity.DogReport;
import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.exceptions.ClientNotFoundException;
import com.example.shelterforpets.repository.ClientRepository;
import com.example.shelterforpets.repository.DogReportsRepository;
import com.example.shelterforpets.repository.DogShelterClientRepository;
import com.example.shelterforpets.repository.VolunteerRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class DogShelterService {

    private final LocalDateTime date = LocalDate.now()
            .atTime(LocalTime.now().getHour(), LocalTime.now().getMinute());


    private final LocalDateTime dateStartDay = LocalDate.now().atTime(0, 0);
    private final LocalDateTime dateEndDay = LocalDate.now().atTime(23, 59, 59);

    private final TelegramBot telegramBot;
    private final ShelterService shelterService;
    private final VolunteerRepository volunteerRepository;
    private final ClientRepository clientRepository;
    private final DogShelterClientRepository dogShelterClientRepository;
    private final NotificationService notificationService;
    private final DogReportsRepository dogReportsRepository;

    public DogShelterService(TelegramBot telegramBot,
                             ShelterService shelterService,
                             VolunteerRepository volunteerRepository,
                             ClientRepository clientRepository,
                             DogShelterClientRepository dogShelterClientRepository,
                             NotificationService notificationService,
                             DogReportsRepository dogReportsRepository) {
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
        this.volunteerRepository = volunteerRepository;
        this.clientRepository = clientRepository;
        this.dogShelterClientRepository = dogShelterClientRepository;
        this.notificationService = notificationService;
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
        notificationService.sendNotification(chatId, shelterInfoText);
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
        notificationService.sendNotification(chatId, scheduleDogShelter);
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
        notificationService.sendNotification(chatId, securityContactDetailsDogShelter);
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
        notificationService.sendNotification(chatId, recommendationInTheCatShelter);
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
    public void saveDogShelterClientNameAndPhoneNumber(long chatId, String name, String phoneNumber) {
        DogShelterClient client = dogShelterClientRepository.findByUserId(chatId).orElseThrow(() ->
                new ClientNotFoundException("Клиента нет в базе данных!"));
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
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void sendDocumentsDogShelter(long chatId) {
        String shelterInfoText = "Список документов, необходимых для того, чтобы взять животное из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForTransportingDogs(long chatId) {
        String shelterInfoText = "Список рекомендаций по транспортировке животного:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsPuppy(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для щенка:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        //!!!!    //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsAdultDogs(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для взрослого животного:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        //!!!!    //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsAdultDogsWithDisabilities(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для животного с " +
                "ограниченными возможностями (зрение, передвижение):\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void adviceToADogHandlerOnPrimaryCommunicationWithADog(long chatId) {
        String shelterInfoText = "Советы кинолога по первичному общению с собакой:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void recommendationOnTheCheckedDogHandler(long chatId) {
        String shelterInfoText = "Рекомендации по проверенным кинологам " +
                "для дальнейшего обращения к ним:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void reasonsWhyTheyMayRefuseAndNotLetYouTakeTheDog(long chatId) {
        String shelterInfoText = "Список причин, почему могут отказать " +
                "и не дать забрать собаку из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";    //!!!!
        //ЗДЕСЬ НУЖНО ДОПОЛНИТЬ КОНКРЕТНУЮ ИНФОРМАЦИЮ О ПРИЮТЕ ДЛЯ СОБАК
        notificationService.sendNotification(chatId, shelterInfoText);
    }

    public void savePhotoReport(Update update) {
        long chatId = update.message().chat().id();
        if (checkReport(update) && dogReportsRepository.findByUserId(chatId).getPhotoPet() == null) {
            DogReport dogReport = dogReportsRepository.findByUserId(chatId);
            PhotoSize[] photoSizes = update.message().photo();
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
                dogReportsRepository.save(dogReport);
                notificationService.sendNotification(update.message().chat().id(),
                        "Ваш отчет зарегистрирован!");
            }
        } else if (!checkReport(update)) {
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
            notificationService.sendNotification(update.message().chat().id(),
                    "Осталось отправить лишь отчет по указанной форме! :)");
        } else {
            notificationService.sendNotification(update.message().chat().id(),
                    "Вы уже отправляли отчет за прошедшие сутки!");
        }

    }

    public void saveStringReport(Update update) {
        long chatId = update.message().chat().id();
        if (checkReport(update) && dogReportsRepository.findByUserId(chatId).getPetReport() == null) {
            DogReport dogReport = dogReportsRepository.findByUserId(chatId);
            dogReport.setPetReport(update.message().text());
            dogReportsRepository.save(dogReport);
            notificationService.sendNotification(update.message().chat().id(),
                    "Ваш отчет зарегистрирован!");
        } else if (!checkReport(update)) {
            DogReport dogReport = new DogReport();
            dogReport.setUserId(update.message().chat().id());
            dogReport.setPetReport(update.message().text());
            dogReport.setDateReport(date);
            dogReportsRepository.save(dogReport);
            notificationService.sendNotification(update.message().chat().id(),
                    "Осталось отправить лишь фото питомца! :)");
        } else {
            notificationService.sendNotification(update.message().chat().id(),
                    "Вы уже отправляли отчет за прошедшие сутки!");
        }
    }

    private boolean checkReport(Update update) {
        long chatId = update.message().chat().id();
        List<DogReport> catReports = dogReportsRepository.findAllByUserId(chatId);
        boolean result = false;
        for (DogReport report : catReports) {
            LocalDateTime dateReport = report.getDateReport();
            if (dateReport.isAfter(dateStartDay) && dateReport.isBefore(dateEndDay)) {
                result = true;
            }
        }
        return result;
    }



    public void findClientsWithAnOverdueDateOfReports() {
        Collection<DogReport> dogReports = dogReportsRepository.
                findAll().
                stream().
                filter(dogReport -> dogReport.getDateReport().isBefore(date.minusDays(2))).
                collect(Collectors.toList());
        dogReports.forEach(dogReport ->notificationService.
                sendNotification(volunteerRepository.findVolunteerByName("НиколайВолонтер"), //Name Volunteer for example,
                        "Пользователь " + dogReport.getUserId() +
                                " плохо заполняет отчет о животном"));
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
        if (!dogShelterClientRepository.existsByUserId(userId)) {
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
        if (dogShelterClientRepository.existsByUserId(userId)) {
            DogShelterClient client = dogShelterClientRepository.findDogShelterClientByUserId(userId);
            client.setName(dogShelterClient.getName());
            client.setStatus(dogShelterClient.getStatus());
            client.setNickNamePet(dogShelterClient.getNickNamePet());
            client.setPhoneNumber(dogShelterClient.getPhoneNumber());
            Status status = dogShelterClient.getStatus();
            switch (status) {
                case ADDITIONAL_PROBATION_SMALL:
                    notificationService.sendNotification(userId,
                            "Вам назначено дополнительное время испытательного срока (14 дней)");
                    break;
                case ADDITIONAL_PROBATION_LARGE:
                    notificationService.sendNotification(userId,
                            "Вам назначено дополнительное время испытательного срока (30 дней)");
                    break;
                case SUCCESS_PROBATION:
                    notificationService.sendNotification(userId,
                            "Вы прошли испытательный срок! Поздравляю!");
                    break;
                case NOT_PASS_PROBATION:
                    notificationService.sendNotification(userId,
                            "К сожалению, Вы не прошли испытательный срок! С вами свяжется волонтер!");
                    break;
            }
            return dogShelterClientRepository.save(client);
        }
        return null;
    }

    /**
     * Delete client by id in dog shelter repository
     *
     * @param userId The ID of the user ID in repository
     */
    public void deleteClientFromDogShelter(long userId) {
        if (dogShelterClientRepository.existsByUserId(userId)) {
            dogShelterClientRepository.deleteById(userId);
        }
    }

}
