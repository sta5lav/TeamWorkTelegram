package com.example.shelterforpets;

import com.example.shelterforpets.constants.Step;
import com.example.shelterforpets.service.*;
import com.example.shelterforpets.service.menu.DogMenuService;
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
        String message = "Записать контактные данные для связи";

        dogMenuService.dogShelterInfoMenu(chatId, message);

        verify(dogShelterService).nameAndPhoneNumberPattern(chatId);
    }

    @Test
    public void testDogShelterInfoMenu_ChooseGetHelpingVolunteers() {
        long chatId = 1238L;
        String message = "Позвать волонтера";

        dogMenuService.dogShelterInfoMenu(chatId, message);

        verify(dogShelterService).getHelpingDogShelterVolunteers(chatId);
    }

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

    @Test
    public void testDogShelterConsultantMenu_ChooseRulesForGettingToKnowAnAnimal() {
        long chatId = 1238L;
        String message = "Правила знакомства с животным до того, как забрать его из приюта";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).sendRulesDogShelter(chatId);
    }

    @Test
    public void testDogShelterConsultantMenu_ChooseAListOfDocumentsRequiredToTakeAnAnimal() {
        long chatId = 1238L;
        String message = "Список документов, необходимых для того, чтобы взять животное из приюта";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).sendDocumentsDogShelter(chatId);
    }

    @Test
    public void testDogShelterConsultantMenu_ChooseListOfRecommendationsForTransportingAnAnimal() {
        long chatId = 1238L;
        String message = "Список рекомендаций по транспортировке животного";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).sendRecommendationsForTransportingDogs(chatId);
    }

    @Test
    public void testDogShelterConsultantMenu_ChooseHomeImprovementForAPuppy() {
        long chatId = 1238L;
        String message = "Список рекомендаций по обустройству дома для щенка";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).sendRecommendationsForImprovementsPuppy(chatId);
    }

    @Test
    public void testDogShelterConsultantMenu_ChooseHomeImprovementForAnAdultAnimal() {
        long chatId = 1238L;
        String message = "Список рекомендаций по обустройству дома для взрослого животного";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).sendRecommendationsForImprovementsAdultDogs(chatId);
    }

    @Test
    public void testDogShelterConsultantMenu_ChooseHomeImprovementForAnAnimalWithDisabilities() {
        long chatId = 1238L;
        String message = "Обустройство дома для животного с ограниченными возможностями (зрение, передвижение)";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).sendRecommendationsForImprovementsAdultDogsWithDisabilities(chatId);
    }

    @Test
    public void testDogShelterConsultantMenu_ChooseGiveAdviceToADogHandlerOnPrimaryCommunicationWithADog() {
        long chatId = 1238L;
        String message = "Советы кинолога по первичному общению с собакой";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).adviceToADogHandlerOnPrimaryCommunicationWithADog(chatId);
    }

    @Test
    public void testDogShelterConsultantMenu_ChooseGiveRecommendationsOnTheCheckedDogHandlers() {
        long chatId = 1238L;
        String message = "Рекомендации по проверенным кинологам для дальнейшего обращения к ним";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).recommendationOnTheCheckedDogHandler(chatId);
    }

    @Test
    public void testDogShelterConsultantMenu_ChooseRecordContactDetailsForCommunication() {
        long chatId = 1238L;
        String message = "Записать контактные данные для связи";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).nameAndPhoneNumberPattern(chatId);
    }

    @Test
    public void testDogShelterConsultantMenu_ChooseReasonsWhyTheyMayRefuseAndNotLetYouTakeTheDog() {
        long chatId = 1238L;
        String message = "Список причин, почему могут отказать и не дать забрать собаку из приюта";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).reasonsWhyTheyMayRefuseAndNotLetYouTakeTheDog(chatId);
    }

    @Test
    public void testDogShelterConsultantMenu_ChooseCallAVolunteer() {
        long chatId = 1238L;
        String message = "Позвать волонтера";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).getHelpingDogShelterVolunteers(chatId);
    }

    @Test
    public void testDogShelterConsultantMenu_ChooseBackToMenuShelter() {
        long chatId = 1238L;
        String message = "Вернуться в меню приюта";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).dogShelterMenu(chatId);
        verify(shelterService).saveClient(chatId, Step.DOG_SHELTER_MENU);
    }

    @Test
    public void testDogShelterConsultantMenu_SaveDogShelterClientNameAndPhoneNumber() {
        long chatId = 1238L;
        String message = "88005553535 Андрей";
        String name = "Андрей";
        String phoneNumber = "88005553535";

        dogMenuService.dogShelterConsultantMenu(chatId, message);

        verify(dogShelterService).saveDogShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);
        verify(telegramBot).execute(any(SendMessage.class));
    }
}
