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
public class CatMenuServiceTests {
    private CatMenuService catMenuService;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ShelterService shelterService;
    @Mock
    private CatShelterService catShelterService;

    @BeforeEach
    public void init() {
        catMenuService = new CatMenuService(telegramBot, shelterService, catShelterService);
    }

    @Test
    public void testCatShelterMenu_ChooseCatShelterInfo() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "Узнать информацию о приюте";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);

        catMenuService.catShelterMenu(update, chatId, text);

        verify(catShelterService).catInfoShelterMenu(chatId);
        verify(shelterService).saveClient(chatId, Step.CAT_SHELTER_INFO_MENU);
    }

    @Test
    public void testCatShelterMenu_ChooseCatAdoptionInstructions() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "Как взять животное из приюта";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);

        catMenuService.catShelterMenu(update, chatId, text);

        verify(catShelterService).catAdoptionInstructions(chatId);
    }

    @Test
    public void testCatShelterMenu_ChooseReport() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "Прислать отчет о питомце";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);

        catMenuService.catShelterMenu(update, chatId, text);

        verify(catShelterService).report(chatId);
    }

    @Test
    public void testCatShelterInfoMenu_ChooseCatShelterInfo() {
        long chatId = 1238L;
        String message = "О приюте";

        catMenuService.catShelterInfoMenu(chatId, message);

        verify(catShelterService).sendCatShelterInfo(chatId);
    }

    @Test
    public void testCatShelterInfoMenu_ChooseSchedule() {
        long chatId = 1238L;
        String message = "Расписание работы приюта и адрес, схема проезда";

        catMenuService.catShelterInfoMenu(chatId, message);

        verify(catShelterService).scheduleCatShelter(chatId);
    }

    @Test
    public void testCatShelterInfoMenu_ChooseSecurityContactDetails() {
        long chatId = 1238L;
        String message = "Контактные данные охраны для оформления пропуска на машину";

        catMenuService.catShelterInfoMenu(chatId, message);

        verify(catShelterService).securityContactDetailsCatShelter(chatId);
    }

    @Test
    public void testCatShelterInfoMenu_ChooseRecommendation() {
        long chatId = 1238L;
        String message = "Общие рекомендации о технике безопасности на территории приюта";

        catMenuService.catShelterInfoMenu(chatId, message);

        verify(catShelterService).recommendationInTheCatShelter(chatId);
    }

    @Test
    public void testCatShelterInfoMenu_ChooseNameAndPhoneNumberPattern() {
        long chatId = 1238L;
        String message = "Принять и записать контактные данные для связи";

        catMenuService.catShelterInfoMenu(chatId, message);

        verify(catShelterService).nameAndPhoneNumberPattern(chatId);
    }

    @Test
    public void testCatShelterInfoMenu_ChooseGetHelpingVolunteers() {
        long chatId = 1238L;
        String message = "Позвать волонтера";

        catMenuService.catShelterInfoMenu(chatId, message);

        verify(catShelterService).getHelpingCatShelterVolunteers(chatId);
    }

    @Test
    public void testCatShelterInfoMenu_ChooseReturnToMenu() {
        long chatId = 1238L;
        String message = "Вернуться в меню приюта";

        catMenuService.catShelterInfoMenu(chatId, message);

        verify(catShelterService).catShelterMenu(chatId);
        verify(shelterService).saveClient(chatId, Step.CAT_SHELTER_MENU);
    }

    @Test
    public void testCatShelterInfoMenu_SaveCatShelterClientNameAndPhoneNumber() {
        long chatId = 1238L;
        String message = "88005553535 Андрей";
        String name = "Андрей";
        String phoneNumber = "88005553535";

        catMenuService.catShelterInfoMenu(chatId, message);

        verify(catShelterService).saveCatShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);
        verify(telegramBot).execute(any(SendMessage.class));
    }
}
