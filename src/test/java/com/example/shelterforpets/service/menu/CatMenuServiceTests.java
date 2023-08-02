package com.example.shelterforpets.service.menu;

import com.example.shelterforpets.constants.Step;
import com.example.shelterforpets.service.CatShelterService;
import com.example.shelterforpets.service.ShelterService;
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
        String message = "Записать контактные данные для связи";

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

    @Test
    public void testCatShelterConsultantMenu_ChooseRulesForGettingToKnowAnAnimal() {
        long chatId = 1238L;
        String message = "Правила знакомства с животным до того, как забрать его из приюта";

        catMenuService.catShelterConsultantMenu(chatId, message);

        verify(catShelterService).sendRulesCatShelter(chatId);
    }

    @Test
    public void testCatShelterConsultantMenu_ChooseAListOfDocumentsRequiredToTakeAnAnimal() {
        long chatId = 1238L;
        String message = "Список документов, необходимых для того, чтобы взять животное из приюта";

        catMenuService.catShelterConsultantMenu(chatId, message);

        verify(catShelterService).sendDocumentsCatShelter(chatId);
    }

    @Test
    public void testCatShelterConsultantMenu_ChooseListOfRecommendationsForTransportingAnAnimal() {
        long chatId = 1238L;
        String message = "Список рекомендаций по транспортировке животного";

        catMenuService.catShelterConsultantMenu(chatId, message);

        verify(catShelterService).sendRecommendationsForTransportingCats(chatId);
    }

    @Test
    public void testCatShelterConsultantMenu_ChooseHomeImprovementForAKitten() {
        long chatId = 1238L;
        String message = "Список рекомендаций по обустройству дома для котенка";

        catMenuService.catShelterConsultantMenu(chatId, message);

        verify(catShelterService).sendRecommendationsForImprovementsKitten(chatId);
    }

    @Test
    public void testCatShelterConsultantMenu_ChooseHomeImprovementForAnAdultAnimal() {
        long chatId = 1238L;
        String message = "Список рекомендаций по обустройству дома для взрослого животного";

        catMenuService.catShelterConsultantMenu(chatId, message);

        verify(catShelterService).sendRecommendationsForImprovementsAdultCats(chatId);
    }

    @Test
    public void testCatShelterConsultantMenu_ChooseHomeImprovementForAnAnimalWithDisabilities() {
        long chatId = 1238L;
        String message = "Обустройство дома для животного с ограниченными возможностями (зрение, передвижение)";

        catMenuService.catShelterConsultantMenu(chatId, message);

        verify(catShelterService).sendRecommendationsForImprovementsAdultCatsWithDisabilities(chatId);
    }

    @Test
    public void testCatShelterConsultantMenu_ChooseRecordContactDetailsForCommunication() {
        long chatId = 1238L;
        String message = "Записать контактные данные для связи";

        catMenuService.catShelterConsultantMenu(chatId, message);

        verify(catShelterService).nameAndPhoneNumberPattern(chatId);
    }

    @Test
    public void testCatShelterConsultantMenu_ChooseCallAVolunteer() {
        long chatId = 1238L;
        String message = "Позвать волонтера";

        catMenuService.catShelterConsultantMenu(chatId, message);

        verify(catShelterService).getHelpingCatShelterVolunteers(chatId);
    }

    @Test
    public void testCatShelterConsultantMenu_ChooseBackToMenuShelter() {
        long chatId = 1238L;
        String message = "Вернуться в меню приюта";

        catMenuService.catShelterConsultantMenu(chatId, message);

        verify(catShelterService).catShelterMenu(chatId);
        verify(shelterService).saveClient(chatId, Step.CAT_SHELTER_MENU);
    }

    @Test
    public void testCatShelterConsultantMenu_SaveCatShelterClientNameAndPhoneNumber() {
        long chatId = 1238L;
        String message = "88005553535 Андрей";
        String name = "Андрей";
        String phoneNumber = "88005553535";

        catMenuService.catShelterConsultantMenu(chatId, message);

        verify(catShelterService).saveCatShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);
        verify(telegramBot).execute(any(SendMessage.class));
    }

    @Test
    public void testCatShelterReportMenu_BackToMenu() {
        long chatId = 123456789L;
        String text = "Вернуться в меню приюта";

        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn(text);
        when(chat.id()).thenReturn(chatId);

        catMenuService.catShelterReportMenu(update);

        verify(catShelterService).catShelterMenu(chatId);
        verify(shelterService).saveClient(chatId, Step.CAT_SHELTER_MENU);
    }
}
