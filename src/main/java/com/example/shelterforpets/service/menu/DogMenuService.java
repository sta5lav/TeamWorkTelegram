package com.example.shelterforpets.service.menu;

import com.example.shelterforpets.entity.Step;
import com.example.shelterforpets.service.DogShelterService;
import com.example.shelterforpets.service.ShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.shelterforpets.constants.Constants.*;

@Service
public class DogMenuService {

    private static final Pattern PATTERN = Pattern.compile("(\\d+) ([А-я\\d.,!?:\\s]+)");

    private final TelegramBot telegramBot;
    private final ShelterService shelterService;
    private final DogShelterService dogShelterService;

    public DogMenuService(TelegramBot telegramBot, ShelterService shelterService, DogShelterService dogShelterService) {
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
        this.dogShelterService = dogShelterService;
    }

    public void dogShelterMenu(Update update, long chatId, String message) {
        String userName = update.message().chat().username();
        String firstName = update.message().chat().firstName();
        switch (message) {
            case FOUND_INFO_ABOUT_SHELTER:
                dogShelterService.dogInfoShelterMenu(chatId);
                shelterService.saveClient(chatId, Step.DOG_SHELTER_INFO_MENU);
                break;
            case HOW_TAKE_A_ANIMAL:
                dogShelterService.dogAdoptionInstructions(chatId);
                shelterService.saveClient(chatId, Step.DOG_SHELTER_CONSULTATION_MENU);
                break;
            case SEND_A_PET_REPORT:
                dogShelterService.report(chatId);
                break;
            default:
                dogShelterService.messageHelpingVolunteers(chatId, firstName, userName);
        }
    }

    public void dogShelterInfoMenu(long chatId, String message) {
        Matcher matcher;
        switch (message) {
            case ABOUT_SHELTER:
                dogShelterService.sendDogShelterInfo(chatId);
                break;
            case SCHEDULE_OF_SHELTER:
                dogShelterService.scheduleDogShelter(chatId);
                break;
            case CONTACTS_FOR_ACCESS:
                dogShelterService.securityContactDetailsDogShelter(chatId);
                break;
            case RECOMMENDATION_OF_SAFETY:
                dogShelterService.recommendationInTheDogShelter(chatId);
                break;
            case RECORD_CONTACT_DETAILS_FOR_COMMUNICATION:
                dogShelterService.nameAndPhoneNumberPattern(chatId);
                break;
            case CALL_A_VOLUNTEER:
                dogShelterService.getHelpingDogShelterVolunteers(chatId);
                break;
            case BACK_TO_MENU_SHELTER:
                dogShelterService.dogShelterMenu(chatId);
                shelterService.saveClient(chatId, Step.DOG_SHELTER_MENU);
                break;
            default:
                if ((matcher = PATTERN.matcher(message)).matches()) {
                    String phoneNumber = matcher.group(1);
                    String name = matcher.group(2);
                    dogShelterService.saveDogShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);
                    SendMessage sendMessage = new SendMessage(chatId, "Ваши данные успешно сохранены!");
                    telegramBot.execute(sendMessage);
                }
        }
    }

    //Методы для работы с меню приобретения животного
    public void dogShelterConsultantMenu(long chatId, String message) {
        Matcher matcher;
        switch(message) {
            case RULES_FOR_GETTING_TO_KNOW_AN_ANIMAL:
                dogShelterService.sendRulesDogShelter(chatId);
                break;
            case A_LIST_OF_DOCUMENTS_REQUIRED_TO_TAKE_AN_ANIMAL:
                dogShelterService.sendDocumentsDogShelter(chatId);
                break;
            case LIST_OF_RECOMMENDATIONS_FOR_TRANSPORTING_AN_ANIMAL:
                dogShelterService.sendRecommendationsForTransportingDogs(chatId);
                break;
            case HOME_IMPROVEMENT_FOR_A_PUPPY:
                dogShelterService.sendRecommendationsForImprovementsPuppy(chatId);
                break;
            case HOME_IMPROVEMENT_FOR_AN_ADULT_ANIMAL:
                dogShelterService.sendRecommendationsForImprovementsAdultDogs(chatId);
                break;
            case HOME_IMPROVEMENT_FOR_AN_ANIMAL_WITH_DISABILITIES:
                dogShelterService.sendRecommendationsForImprovementsAdultDogsWithDisabilities(chatId);
                break;
            case GIVE_ADVICE_TO_A_DOG_HANDLER_ON_PRIMARY_COMMUNICATION_WITH_A_DOG:
                dogShelterService.adviceToADogHandlerOnPrimaryCommunicationWithADog(chatId);
                break;
            case GIVE_RECOMMENDATIONS_ON_THE_CHECKED_DOG_HANDLERS:
                dogShelterService.recommendationOnTheCheckedDogHandler(chatId);
                break;
            case RECORD_CONTACT_DETAILS_FOR_COMMUNICATION:
                dogShelterService.nameAndPhoneNumberPattern(chatId);
                break;
            case REASONS_WHY_THEY_MAY_REFUSE_AND_NOT_LET_YOU_TAKE_THE_DOG:
                dogShelterService.reasonsWhyTheyMayRefuseAndNotLetYouTakeTheDog(chatId);
                break;
            case CALL_A_VOLUNTEER:
                dogShelterService.getHelpingDogShelterVolunteers(chatId);
                break;
            case BACK_TO_MENU_SHELTER:
                dogShelterService.dogShelterMenu(chatId);
                shelterService.saveClient(chatId, Step.DOG_SHELTER_MENU);
                break;
            default:
                if ((matcher = PATTERN.matcher(message)).matches()) {
                    String phoneNumber = matcher.group(1);
                    String name = matcher.group(2);
                    dogShelterService.saveDogShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);
                    SendMessage sendMessage = new SendMessage(chatId, "Ваши данные успешно сохранены!");
                    telegramBot.execute(sendMessage);
                }
        }
    }
}
