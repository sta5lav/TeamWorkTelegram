package com.example.shelterforpets;

import com.example.shelterforpets.entity.Step;
import com.example.shelterforpets.service.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DogMenuServiceTest {
    private DogMenuService dogMenuService;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ShelterService shelterService;
    @Mock
    private DogShelterService dogShelterService;

    @BeforeEach
    public void init() {
        dogMenuService = new DogMenuService(telegramBot, shelterService, dogShelterService);
    }

    @Test
    public void testDogShelterMenu_ChooseDogShelterInfo() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "Узнать информацию о приюте";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);

        dogMenuService.dogShelterMenu(update, chatId, text);

        verify(dogShelterService).dogInfoShelterMenu(chatId);
        verify(shelterService).saveClient(chatId, Step.DOG_SHELTER_INFO_MENU);
    }

    @Test
    public void testDogShelterMenu_ChooseDogAdoptionInstructions() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "Как взять животное из приюта";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);

        dogMenuService.dogShelterMenu(update, chatId, text);

        verify(dogShelterService).dogAdoptionInstructions(chatId);
    }

    @Test
    public void testDogShelterMenu_ChooseReport() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "Прислать отчет о питомце";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);

        dogMenuService.dogShelterMenu(update, chatId, text);

        verify(dogShelterService).report(chatId);
    }

    @Test
    public void testDogShelterInfoMenu_ChooseDogShelterInfo() {
        long chatId = 1238L;
        String message = "О приюте";

        dogMenuService.dogShelterInfoMenu(chatId, message);

        verify(dogShelterService).sendDogShelterInfo(chatId);
    }

    @Test
    public void testDogShelterInfoMenu_ChooseSchedule() {
        long chatId = 1238L;
        String message = "Расписание работы приюта и адрес, схема проезда";

        dogMenuService.dogShelterInfoMenu(chatId, message);

        verify(dogShelterService).scheduleDogShelter(chatId);
    }

    @Test
    public void testDogShelterInfoMenu_ChooseSecurityContactDetails() {
        long chatId = 1238L;
        String message = "Контактные данные охраны для оформления пропуска на машину";

        dogMenuService.dogShelterInfoMenu(chatId, message);

        verify(dogShelterService).securityContactDetailsDogShelter(chatId);
    }

    @Test
    public void testDogShelterInfoMenu_ChooseRecommendation() {
        long chatId = 1238L;
        String message = "Общие рекомендации о технике безопасности на территории приюта";

        dogMenuService.dogShelterInfoMenu(chatId, message);

        verify(dogShelterService).recommendationInTheDogShelter(chatId);
    }

    @Test
    public void testDogShelterInfoMenu_ChooseNameAndPhoneNumberPattern() {
        long chatId = 1238L;
        String message = "Принять и записать контактные данные для связи";

        dogMenuService.dogShelterInfoMenu(chatId, message);

        verify(dogShelterService).nameAndPhoneNumberPattern(chatId);
    }

    /*
    @Test
    public void testDogShelterInfoMenu_ChooseGetHelpingVolunteers() {
        long chatId = 1238L;
        String message = "Позвать волонтера";

        dogMenuService.dogShelterInfoMenu(chatId, message);

        verify(dogShelterService).getHelpingDogShelterVolunteers(chatId);
    }
     */

    @Test
    public void testDogShelterInfoMenu_ChooseReturnToMenu() {
        long chatId = 1238L;
        String message = "Вернуться в меню приюта";

        dogMenuService.dogShelterInfoMenu(chatId, message);

        verify(dogShelterService).dogShelterMenu(chatId);
        verify(shelterService).saveClient(chatId, Step.DOG_SHELTER_MENU);
    }

    @Test
    public void testDogShelterInfoMenu_SaveDogShelterClientNameAndPhoneNumber() {
        long chatId = 1238L;
        String message = "88005553535 Андрей";
        String name = "Андрей";
        String phoneNumber = "88005553535";

        dogMenuService.dogShelterInfoMenu(chatId, message);

        verify(dogShelterService).saveDogShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);
        verify(telegramBot).execute(any(SendMessage.class));
    }
}
