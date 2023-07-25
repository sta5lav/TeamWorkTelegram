package com.example.shelterforpets;

import com.example.shelterforpets.constants.Status;
import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.repository.ClientRepository;
import com.example.shelterforpets.repository.DogReportsRepository;
import com.example.shelterforpets.repository.DogShelterClientRepository;
import com.example.shelterforpets.repository.VolunteerRepository;
import com.example.shelterforpets.service.DogShelterService;
import com.example.shelterforpets.service.NotificationService;
import com.example.shelterforpets.service.ShelterService;
import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DogShelterServiceTests {
    private DogShelterService dogShelterService;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ShelterService shelterService;
    @Mock
    private VolunteerRepository volunteerRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private DogShelterClientRepository dogShelterClientRepository;
    @Mock
    private NotificationService notificationService;
    @Mock
    private DogReportsRepository dogReportsRepository;

    @BeforeEach
    public void init() {
        dogShelterService = new DogShelterService(telegramBot, shelterService,
                volunteerRepository, clientRepository, dogShelterClientRepository, notificationService, dogReportsRepository);
    }

    @Test
    public void testDogShelterMenu() {
        long chatId = 1238L;

        dogShelterService.dogShelterMenu(chatId);

        verify(shelterService).sendInfoShelterMenu(chatId);
    }

    @Test
    public void testDogInfoShelterMenu() {
        long chatId = 1238L;

        dogShelterService.dogInfoShelterMenu(chatId);

        verify(shelterService).infoShelterMenu(chatId);
    }

    @Test
    public void testDogAdoptionInstructions() {
        long chatId = 1238L;
        dogShelterService.dogAdoptionInstructions(chatId);

        verify(shelterService).clientAdoptionInstructionsForDogShelter(chatId);
    }

    @Test
    public void testReport() {
        long chatId = 1238L;

        dogShelterService.report(chatId);

        verify(shelterService).sendPetReport(chatId);
    }

    @Test
    public void testMessageHelpingVolunteers() {
        long chatId = 1238L;
        String firstName = "John";
        String userName = "john123";

        dogShelterService.messageHelpingVolunteers(chatId, firstName, userName);

        verify(shelterService).sendMessageHelpingVolunteers(chatId, firstName, userName);
    }

    @Test
    public void testSendDogShelterInfo() {
        long chatId = 1238L;

        dogShelterService.sendDogShelterInfo(chatId);

        String expectedText = "Информация о приюте:\n" +
                "Адрес: ...\n" +
                "Телефон: ...\n" +
                "Email: ...";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testScheduleDogShelter() {
        long chatId = 1238L;

        dogShelterService.scheduleDogShelter(chatId);

        String expectedText = "Информация о работе приюта:\n" +
                "Время работы: ...\n" +
                "Адрес: ...\n" +
                "Схема проезда: ...";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testSecurityContactDetailsDogShelter() {
        long chatId = 1238L;

        dogShelterService.securityContactDetailsDogShelter(chatId);

        String expectedText = "Контактные данные охраны приюта:\n" +
                "Телефон: ...\n" +
                "Имя: ...";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testRecommendationInTheDogShelter() {
        long chatId = 1238L;

        dogShelterService.recommendationInTheDogShelter(chatId);

        String expectedText = "Техника безопасности:\n" +
                "1. ...\n" +
                "2. ...";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testNameAndPhoneNumberPattern() {
        long chatId = 1238L;

        dogShelterService.nameAndPhoneNumberPattern(chatId);

        verify(shelterService).sendNameAndPhoneNumberPattern(chatId);
    }

    @Test
    public void testSaveDogShelterClientNameAndPhoneNumber() {
        DogShelterClient client = mock(DogShelterClient.class);
        when(dogShelterClientRepository.findByUserId(anyLong())).thenReturn(Optional.of(client));

        long chatId = 1238L;
        String name = "John";
        String phoneNumber = "88005553535";

        dogShelterService.saveDogShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);

        verify(dogShelterClientRepository).findByUserId(chatId);
        verify(client).setName(name);
        verify(client).setPhoneNumber(phoneNumber);
        verify(dogShelterClientRepository).save(client);
    }

    @Test
    public void testSendRulesDogShelter() {
        long chatId = 1238L;

        dogShelterService.sendRulesDogShelter(chatId);

        String expectedText = "Правила знакомства с животным до того, как забрать его из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testSendDocumentsDogShelter() {
        long chatId = 1238L;

        dogShelterService.sendDocumentsDogShelter(chatId);

        String expectedText = "Список документов, необходимых для того, чтобы взять животное из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId,expectedText);
    }

    @Test
    public void testSendRecommendationsForTransportingDogs() {
        long chatId = 1238L;

        dogShelterService.sendRecommendationsForTransportingDogs(chatId);

        String expectedText = "Список рекомендаций по транспортировке животного:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testSendRecommendationsForImprovementsKitten() {
        long chatId = 1238L;

        dogShelterService.sendRecommendationsForImprovementsPuppy(chatId);

        String expectedText = "Список рекомендаций по обустройству дома для щенка:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testSendRecommendationsForImprovementsAdultDogs() {
        long chatId = 1238L;

        dogShelterService.sendRecommendationsForImprovementsAdultDogs(chatId);

        String expectedText = "Список рекомендаций по обустройству дома для взрослого животного:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testSendRecommendationsForImprovementsAdultDogsWithDisabilities() {
        long chatId = 1238L;

        dogShelterService.sendRecommendationsForImprovementsAdultDogsWithDisabilities(chatId);

        String expectedText = "Список рекомендаций по обустройству дома для животного с "
                + "ограниченными возможностями (зрение, передвижение):\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testAdviceToADogHandlerOnPrimaryCommunicationWithADog() {
        long chatId = 1238L;

        dogShelterService.adviceToADogHandlerOnPrimaryCommunicationWithADog(chatId);

        String expectedText = "Советы кинолога по первичному общению с собакой:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testRecommendationOnTheCheckedDogHandler() {
        long chatId = 1238L;

        dogShelterService.recommendationOnTheCheckedDogHandler(chatId);

        String expectedText = "Рекомендации по проверенным кинологам " +
                "для дальнейшего обращения к ним:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testReasonsWhyTheyMayRefuseAndNotLetYouTakeTheDog() {
        long chatId = 1238L;

        dogShelterService.reasonsWhyTheyMayRefuseAndNotLetYouTakeTheDog(chatId);

        String expectedText = "Список причин, почему могут отказать " +
                "и не дать забрать собаку из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testFindClientFromDogShelter() {
        long userId = 123456L;
        DogShelterClient client = new DogShelterClient();
        client.setUserId(userId);

        when(dogShelterClientRepository.findByUserId(userId)).thenReturn(Optional.of(client));

        DogShelterClient result = dogShelterService.findClientFromDogShelter(userId);

        verify(dogShelterClientRepository).findByUserId(userId);
        assertEquals(client, result);
    }

    @Test
    public void testPostClientFromDogShelter() {
        long userId = 123456L;
        DogShelterClient dogShelterClient = new DogShelterClient();
        dogShelterClient.setUserId(userId);

        when(dogShelterClientRepository.existsByUserId(userId)).thenReturn(false);
        when(dogShelterClientRepository.save(dogShelterClient)).thenReturn(dogShelterClient);

        DogShelterClient result = dogShelterService.postClientFromDogShelter(userId, dogShelterClient);

        verify(dogShelterClientRepository).existsByUserId(userId);
        verify(dogShelterClientRepository).save(dogShelterClient);
        assertEquals(dogShelterClient, result);
    }

    @Test
    public void testPutClientFromDogShelter() {
        long userId = 123456L;
        DogShelterClient dogShelterClient = new DogShelterClient();
        dogShelterClient.setUserId(userId);
        dogShelterClient.setNickNamePet("example");
        dogShelterClient.setName("example");
        dogShelterClient.setPhoneNumber("example");
        dogShelterClient.setStatus(Status.PROBATION);

        when(dogShelterClientRepository.findDogShelterClientByUserId(userId)).thenReturn(dogShelterClient);
        when(dogShelterClientRepository.existsByUserId(userId)).thenReturn(true);
        when(dogShelterClientRepository.save(dogShelterClient)).thenReturn(dogShelterClient);

        dogShelterClientRepository.save(dogShelterClient);

        DogShelterClient result = dogShelterService.putClientFromDogShelter(userId, dogShelterClient);

        verify(dogShelterClientRepository).findDogShelterClientByUserId(userId);
        verify(dogShelterClientRepository).existsByUserId(userId);
        verify(dogShelterClientRepository, times(2)).save(dogShelterClient);
        assertEquals(dogShelterClient, result);
    }

    @Test
    public void testDeleteClientFromDogShelter() {
        long userId = 123456L;

        when(dogShelterClientRepository.existsByUserId(userId)).thenReturn(true);

        dogShelterService.deleteClientFromDogShelter(userId);

        verify(dogShelterClientRepository).existsByUserId(userId);
        verify(dogShelterClientRepository).deleteById(userId);
    }
}
