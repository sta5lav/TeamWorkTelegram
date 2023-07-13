package com.example.shelterforpets;

import com.example.shelterforpets.entity.Step;
import com.example.shelterforpets.service.*;
import com.example.shelterforpets.service.menu.CatMenuService;
import com.example.shelterforpets.service.menu.DogMenuService;
import com.example.shelterforpets.service.menu.StartMenuService;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StepServiceTests {
    private StepService stepService;
    @Mock
    private ShelterService shelterService;
    @Mock
    private StartMenuService startMenuService;
    @Mock
    private CatMenuService catMenuService;
    @Mock
    private DogMenuService dogMenuService;

    @BeforeEach
    public void init() {
        stepService = new StepService(shelterService, startMenuService, catMenuService, dogMenuService);
    }

    @Test
    public void testProcess_StartCommand() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "/start";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(text);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);

        stepService.process(update);

        verify(shelterService).sendWelcomeMessage(chatId);
        verify(shelterService).saveClient(chatId, Step.START_MENU);
    }

    @Test
    public void testProcess_StepStartMenu() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "text";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(text);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        when(shelterService.getStepClient(chatId)).thenReturn(Step.START_MENU);

        stepService.process(update);

        verify(startMenuService).startMenu(chatId, text);
    }

    @Test
    public void testProcess_StepCatShelterMenu() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "text";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(text);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        when(shelterService.getStepClient(chatId)).thenReturn(Step.CAT_SHELTER_MENU);

        stepService.process(update);

        verify(catMenuService).catShelterMenu(update, chatId, text);
    }

    @Test
    public void testProcess_StepDogShelterMenu() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "text";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(text);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        when(shelterService.getStepClient(chatId)).thenReturn(Step.DOG_SHELTER_MENU);

        stepService.process(update);

        verify(dogMenuService).dogShelterMenu(update, chatId, text);
    }

    @Test
    public void testProcess_StepCatShelterInfoMenu() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "text";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(text);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        when(shelterService.getStepClient(chatId)).thenReturn(Step.CAT_SHELTER_INFO_MENU);

        stepService.process(update);

        verify(catMenuService).catShelterInfoMenu(chatId, text);
    }

    @Test
    public void testProcess_StepDogShelterInfoMenu() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        String text = "text";
        long chatId = 1238L;
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(text);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        when(shelterService.getStepClient(chatId)).thenReturn(Step.DOG_SHELTER_INFO_MENU);

        stepService.process(update);

        verify(dogMenuService).dogShelterInfoMenu(chatId, text);
    }
}
