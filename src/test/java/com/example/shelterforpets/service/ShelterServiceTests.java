package com.example.shelterforpets.service;

import com.example.shelterforpets.constants.Step;
import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.entity.Client;
import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.repository.CatShelterClientRepository;
import com.example.shelterforpets.repository.ClientRepository;
import com.example.shelterforpets.repository.DogShelterClientRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTests {
    private ShelterService shelterService;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CatShelterClientRepository catShelterClientRepository;
    @Mock
    private DogShelterClientRepository dogShelterClientRepository;
    @Mock
    private NotificationService notificationService;

    @BeforeEach
    public void init() {
        shelterService = new ShelterService(telegramBot, clientRepository,
                catShelterClientRepository, dogShelterClientRepository, notificationService);
    }

    @Test
    public void testInfoShelterMenu() {
        long chatId = 1238L;

        shelterService.infoShelterMenu(chatId);

        verify(notificationService).sendNotificationWithMenu(eq(chatId),
                eq("Что вас интересует?"), any(Keyboard.class));
    }

    @Test
    public void testClientAdoptionInstructionsForCatShelter() {
        long chatId = 1238L;

        shelterService.clientAdoptionInstructionsForCatShelter(chatId);

        verify(notificationService).sendNotificationWithMenu(eq(chatId),
                eq("Выберите пункт меню."), any(Keyboard.class));
    }

    @Test
    public void testClientAdoptionInstructionsForDogShelter() {
        long chatId = 1238L;

        shelterService.clientAdoptionInstructionsForDogShelter(chatId);

        verify(notificationService).sendNotificationWithMenu(eq(chatId),
                eq("Выберите пункт меню."), any(Keyboard.class));
    }

    @Test
    public void testSaveClientInCatShelter_ClientDoesNotExist_SaveClient() {
        long chatId = 1238L;
        when(catShelterClientRepository.existsByUserId(chatId)).thenReturn(false);

        shelterService.saveClientInCatShelter(chatId);

        verify(catShelterClientRepository).existsByUserId(chatId);
        verify(catShelterClientRepository).save(any(CatShelterClient.class));
    }

    @Test
    public void testSaveClientInDogShelter_ClientDoesNotExist_SaveClient() {
        long chatId = 1238L;
        when(dogShelterClientRepository.existsByUserId(chatId)).thenReturn(false);

        shelterService.saveClientInDogShelter(chatId);

        verify(dogShelterClientRepository).existsByUserId(chatId);
        verify(dogShelterClientRepository).save(any(DogShelterClient.class));
    }

    @Test
    public void testSendNameAndPhoneNumberPattern() {
        long chatId = 1238L;

        shelterService.sendNameAndPhoneNumberPattern(chatId);

        String expectedNotification = "Введите контактные данные в формате:\n89991234567 ИМЯ";
        verify(notificationService).sendNotification(chatId, expectedNotification);
    }

    @Test
    public void testSaveClient_ClientDoesNotExist_SaveClient() {
        long chatId = 1238L;
        Step step = Step.START_MENU;
        when(clientRepository.existsByUserId(chatId)).thenReturn(false);

        shelterService.saveClient(chatId, step);

        verify(clientRepository).save(argThat(client -> client.getUserId() == chatId && client.getStep() == step));
    }

    @Test
    public void testSaveClient_ClientExists_UpdateClient() {
        long chatId = 1238L;
        Step step = Step.CAT_SHELTER_MENU;
        Client existingClient = new Client();
        existingClient.setUserId(chatId);
        when(clientRepository.existsByUserId(chatId)).thenReturn(true);
        when(clientRepository.findByUserId(chatId)).thenReturn(existingClient);

        shelterService.saveClient(chatId, step);

        verify(clientRepository).save(argThat(client -> client.getUserId() == chatId && client.getStep() == step));
    }

    @Test
    public void testGetStepClient_ClientExists_ReturnStep() {
        long chatId = 1238L;
        Step expectedStep = Step.CAT_SHELTER_MENU;
        Client existingClient = new Client();
        existingClient.setUserId(chatId);
        existingClient.setStep(expectedStep);
        when(clientRepository.findByUserId(chatId)).thenReturn(existingClient);

        Step result = shelterService.getStepClient(chatId);

        verify(clientRepository).findByUserId(chatId);
        assertEquals(expectedStep, result);
    }

    @Test
    public void testSendShelterMenu() {
        long chatId = 1238L;
        String text = "Выберите приют:";

        shelterService.sendShelterMenu(chatId, text);

        verify(notificationService).sendNotificationWithMenu(eq(chatId), eq(text), any(Keyboard.class));
    }
    @Test
    public void testSendInfoShelterMenu() {
        long chatId = 1238L;
        String text = "Что вас интересует?";

        shelterService.sendInfoShelterMenu(chatId);

        verify(notificationService).sendNotificationWithMenu(eq(chatId), eq(text), any(Keyboard.class));
    }

    @Test
    public void testSendPetReport() {
        long chatId = 1238L;
        shelterService.sendPetReport(chatId);

        String expectedPetReportText = "Прислать отчет необходимо по следующей форме: \n" +
                "1. Фотография питомца; \n" +
                "2. В одном сообщении указать рацион животного, общее самочувствие " +
                "и привыкание к новому месту и необходимо указать изменения в поведении:" +
                " отказ от старых привычек, приобретение новых. \n" +
                "После того, как Вы отправите отчет, волонтеры его проверят и свящутся с Вами, " +
                "если будут замечания";
        verify(notificationService).sendNotification(chatId, expectedPetReportText);
    }

    @Test
    public void testSendMessageHelpingVolunteers() {
        long chatId = 1238L;
        String firstName = "John";
        String userName = "john123";

        shelterService.sendMessageHelpingVolunteers(chatId, firstName, userName);

        String expectedHelpingVolunteers = "К сожалению, я не могу найти ответ на Ваш запрос. Бегу за волонтёром! " +
                "Не волнуйтесь, с Вами свяжутся в ближайшее время! ";
        verify(notificationService).sendNotification(chatId, expectedHelpingVolunteers);
    }
}
