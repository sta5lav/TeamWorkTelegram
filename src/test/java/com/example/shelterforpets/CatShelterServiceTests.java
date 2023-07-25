package com.example.shelterforpets;

import com.example.shelterforpets.constants.Status;
import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.repository.CatReportsRepository;
import com.example.shelterforpets.repository.CatShelterClientRepository;
import com.example.shelterforpets.repository.ClientRepository;
import com.example.shelterforpets.repository.VolunteerRepository;
import com.example.shelterforpets.service.CatShelterService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatShelterServiceTests {
    private CatShelterService catShelterService;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ShelterService shelterService;
    @Mock
    private VolunteerRepository volunteerRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CatShelterClientRepository catShelterClientRepository;
    @Mock
    private NotificationService notificationService;
    @Mock
    CatReportsRepository catReportsRepository;

    @BeforeEach
    public void init() {
        catShelterService = new CatShelterService(telegramBot, shelterService,
                volunteerRepository, clientRepository,catShelterClientRepository, notificationService, catReportsRepository);
    }

    @Test
    public void testCatShelterMenu() {
        long chatId = 1238L;

        catShelterService.catShelterMenu(chatId);

        verify(shelterService).sendInfoShelterMenu(chatId);
    }

    @Test
    public void testCatInfoShelterMenu() {
        long chatId = 1238L;

        catShelterService.catInfoShelterMenu(chatId);

        verify(shelterService).infoShelterMenu(chatId);
    }

    @Test
    public void testCatAdoptionInstructions() {
        long chatId = 1238L;
        catShelterService.catAdoptionInstructions(chatId);

        verify(shelterService).clientAdoptionInstructionsForCatShelter(chatId);
    }

    @Test
    public void testReport() {
        long chatId = 1238L;

        catShelterService.report(chatId);

        verify(shelterService).sendPetReport(chatId);
    }

    @Test
    public void testMessageHelpingVolunteers() {
        long chatId = 1238L;
        String firstName = "John";
        String userName = "john123";

        catShelterService.messageHelpingVolunteers(chatId, firstName, userName);

        verify(shelterService).sendMessageHelpingVolunteers(chatId, firstName, userName);
    }

    @Test
    public void testSendCatShelterInfo() {
        long chatId = 1238L;

        catShelterService.sendCatShelterInfo(chatId);

        String expectedText = "Информация о приюте:\n" +
                "Адрес: ...\n" +
                "Телефон: ...\n" +
                "Email: ...";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testScheduleCatShelter() {
        long chatId = 1238L;

        catShelterService.scheduleCatShelter(chatId);

        String expectedText = "Информация о работе приюта:\n" +
                "Время работы: ...\n" +
                "Адрес: ...\n" +
                "Схема проезда: ...";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testSecurityContactDetailsCatShelter() {
        long chatId = 1238L;

        catShelterService.securityContactDetailsCatShelter(chatId);

        String expectedText = "Контактные данные охраны приюта:\n" +
                "Телефон: ...\n" +
                "Имя: ...";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testRecommendationInTheCatShelter() {
        long chatId = 1238L;

        catShelterService.recommendationInTheCatShelter(chatId);

        String expectedText = "Техника безопасности:\n" +
                "1. ...\n" +
                "2. ...";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testNameAndPhoneNumberPattern() {
        long chatId = 1238L;

        catShelterService.nameAndPhoneNumberPattern(chatId);

        verify(shelterService).sendNameAndPhoneNumberPattern(chatId);
    }

    /*




    тест для getHelpingCatShelterVolunteers




     */

    @Test
    public void testSaveCatShelterClientNameAndPhoneNumber() {
        CatShelterClient client = mock(CatShelterClient.class);
        when(catShelterClientRepository.findByUserId(anyLong())).thenReturn(Optional.of(client));

        long chatId = 1238L;
        String name = "John";
        String phoneNumber = "88005553535";

        catShelterService.saveCatShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);

        verify(catShelterClientRepository).findByUserId(chatId);
        verify(client).setName(name);
        verify(client).setPhoneNumber(phoneNumber);
        verify(catShelterClientRepository).save(client);
    }

    @Test
    public void testSendRulesCatShelter() {
        long chatId = 1238L;

        catShelterService.sendRulesCatShelter(chatId);

        String expectedText = "Правила знакомства с животным до того, как забрать его из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testSendDocumentsCatShelter() {
        long chatId = 1238L;

        catShelterService.sendDocumentsCatShelter(chatId);

        String expectedText = "Список документов, необходимых для того, чтобы взять животное из приюта:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId,expectedText);
    }

    @Test
    public void testSendRecommendationsForTransportingCats() {
        long chatId = 1238L;

        catShelterService.sendRecommendationsForTransportingCats(chatId);

        String expectedText = "Список рекомендаций по транспортировке животного:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testSendRecommendationsForImprovementsKitten() {
        long chatId = 1238L;

        catShelterService.sendRecommendationsForImprovementsKitten(chatId);

        String expectedText = "Список рекомендаций по обустройству дома для котенка:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testSendRecommendationsForImprovementsAdultCats() {
        long chatId = 1238L;

        catShelterService.sendRecommendationsForImprovementsAdultCats(chatId);

        String expectedText = "Список рекомендаций по обустройству дома для взрослого животного:\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testSendRecommendationsForImprovementsAdultCatsWithDisabilities() {
        long chatId = 1238L;

        catShelterService.sendRecommendationsForImprovementsAdultCatsWithDisabilities(chatId);

        String expectedText = "Список рекомендаций по обустройству дома для животного с "
                + "ограниченными возможностями (зрение, передвижение):\n" +
                "1. ...\n" +
                "2. ...\n" +
                "3. ...\n";
        verify(notificationService).sendNotification(chatId, expectedText);
    }

    @Test
    public void testFindClientFromCatShelter() {
        long userId = 123456L;
        CatShelterClient client = new CatShelterClient();
        client.setUserId(userId);

        when(catShelterClientRepository.findByUserId(userId)).thenReturn(Optional.of(client));

        CatShelterClient result = catShelterService.findClientFromCatShelter(userId);

        verify(catShelterClientRepository).findByUserId(userId);
        assertEquals(client, result);
    }

    @Test
    public void testPostClientFromCatShelter() {
        long userId = 123456L;
        CatShelterClient catShelterClient = new CatShelterClient();
        catShelterClient.setUserId(userId);

        when(catShelterClientRepository.existsByUserId(userId)).thenReturn(false);
        when(catShelterClientRepository.save(catShelterClient)).thenReturn(catShelterClient);

        CatShelterClient result = catShelterService.postClientFromCatShelter(userId, catShelterClient);

        verify(catShelterClientRepository).existsByUserId(userId);
        verify(catShelterClientRepository).save(catShelterClient);
        assertEquals(catShelterClient, result);
    }

    @Test
    public void testPutClientFromCatShelter() {
        long userId = 123456L;
        CatShelterClient catShelterClient = new CatShelterClient();
        catShelterClient.setId(1L);
        catShelterClient.setUserId(userId);
        catShelterClient.setNickNamePet("example");
        catShelterClient.setName("example");
        catShelterClient.setPhoneNumber("example");
        catShelterClient.setStatus(Status.PROBATION);

        when(catShelterClientRepository.findCatShelterClientByUserId(userId)).thenReturn(catShelterClient);
        when(catShelterClientRepository.existsByUserId(userId)).thenReturn(true);
        when(catShelterClientRepository.save(catShelterClient)).thenReturn(catShelterClient);

        catShelterClientRepository.save(catShelterClient);

        CatShelterClient result = catShelterService.putClientFromCatShelter(userId, catShelterClient);

        verify(catShelterClientRepository).findCatShelterClientByUserId(userId);
        verify(catShelterClientRepository).existsByUserId(userId);
        verify(catShelterClientRepository, times(2)).save(catShelterClient);
        assertEquals(catShelterClient, result);
    }

    @Test
    public void testDeleteClientFromCatShelter() {
        long userId = 123456L;

        when(catShelterClientRepository.existsByUserId(userId)).thenReturn(true);

        catShelterService.deleteClientFromCatShelter(userId);

        verify(catShelterClientRepository).existsByUserId(userId);
        verify(catShelterClientRepository).deleteById(userId);
    }
}
