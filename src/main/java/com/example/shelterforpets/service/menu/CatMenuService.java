package com.example.shelterforpets.service.menu;

import com.example.shelterforpets.constants.Step;
import com.example.shelterforpets.service.CatShelterService;
import com.example.shelterforpets.service.ShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.shelterforpets.constants.Constants.*;

@Service
public class CatMenuService {

    private static final Pattern PATTERN = Pattern.compile("(\\d+) ([А-я\\d.,!?:\\s]+)");

    private final TelegramBot telegramBot;
    private final ShelterService shelterService;
    private final CatShelterService catShelterService;

    public CatMenuService(TelegramBot telegramBot,
                          ShelterService shelterService,
                          CatShelterService catShelterService) {
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
        this.catShelterService = catShelterService;
    }

    public void catShelterMenu(Update update, long chatId, String message) {
        String userName = update.message().chat().username();
        String firstName = update.message().chat().firstName();
        switch (message) {
            case FOUND_INFO_ABOUT_SHELTER:
                catShelterService.catInfoShelterMenu(chatId);
                shelterService.saveClient(chatId, Step.CAT_SHELTER_INFO_MENU);
                break;
            case HOW_TAKE_A_ANIMAL:
                catShelterService.catAdoptionInstructions(chatId);
                shelterService.saveClient(chatId, Step.CAT_SHELTER_CONSULTATION_MENU);
                break;
            case SEND_A_PET_REPORT:
                catShelterService.report(chatId);
                shelterService.backToMenuShelter(chatId, "Отправьте отчет или вернитесь в меню");
                shelterService.saveClient(chatId, Step.CAT_SHELTER_REPORT_MENU);
                break;
            default:
                catShelterService.messageHelpingVolunteers(chatId, firstName, userName);
                shelterService.saveClient(chatId, Step.START_MENU);
        }
    }

    //Методы для работы с меню информации о приюте
    public void catShelterInfoMenu(long chatId, String message) {
        Matcher matcher;
        switch (message) {
            case ABOUT_SHELTER:
                catShelterService.sendCatShelterInfo(chatId);
                break;
            case SCHEDULE_OF_SHELTER:
                catShelterService.scheduleCatShelter(chatId);
                break;
            case CONTACTS_FOR_ACCESS:
                catShelterService.securityContactDetailsCatShelter(chatId);
                break;
            case RECOMMENDATION_OF_SAFETY:
                catShelterService.recommendationInTheCatShelter(chatId);
                break;
            case RECORD_CONTACT_DETAILS_FOR_COMMUNICATION:
                catShelterService.nameAndPhoneNumberPattern(chatId);
                break;
            case CALL_A_VOLUNTEER:
                catShelterService.getHelpingCatShelterVolunteers(chatId);
                break;
            case BACK_TO_MENU_SHELTER:
                catShelterService.catShelterMenu(chatId);
                shelterService.saveClient(chatId, Step.CAT_SHELTER_MENU);
                break;
            default:
                if ((matcher = PATTERN.matcher(message)).matches()) {
                    String phoneNumber = matcher.group(1);
                    String name = matcher.group(2);
                    catShelterService.saveCatShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);
                    SendMessage sendMessage = new SendMessage(chatId, "Ваши данные успешно сохранены!");
                    telegramBot.execute(sendMessage);
                }
        }
    }

    //Методы для работы с меню приобретения животного
    public void catShelterConsultantMenu(long chatId, String message) {
        Matcher matcher;
        switch(message) {
            case RULES_FOR_GETTING_TO_KNOW_AN_ANIMAL:
                catShelterService.sendRulesCatShelter(chatId);
                break;
            case A_LIST_OF_DOCUMENTS_REQUIRED_TO_TAKE_AN_ANIMAL:
                catShelterService.sendDocumentsCatShelter(chatId);
                break;
            case LIST_OF_RECOMMENDATIONS_FOR_TRANSPORTING_AN_ANIMAL:
                catShelterService.sendRecommendationsForTransportingCats(chatId);
                break;
            case HOME_IMPROVEMENT_FOR_A_KITTEN:
                catShelterService.sendRecommendationsForImprovementsKitten(chatId);
                break;
            case HOME_IMPROVEMENT_FOR_AN_ADULT_ANIMAL:
                catShelterService.sendRecommendationsForImprovementsAdultCats(chatId);
                break;
            case HOME_IMPROVEMENT_FOR_AN_ANIMAL_WITH_DISABILITIES:
                catShelterService.sendRecommendationsForImprovementsAdultCatsWithDisabilities(chatId);
                break;
            case RECORD_CONTACT_DETAILS_FOR_COMMUNICATION:
                catShelterService.nameAndPhoneNumberPattern(chatId);
                break;
            case CALL_A_VOLUNTEER:
                catShelterService.getHelpingCatShelterVolunteers(chatId);
                break;
            case BACK_TO_MENU_SHELTER:
                catShelterService.catShelterMenu(chatId);
                shelterService.saveClient(chatId, Step.CAT_SHELTER_MENU);
                break;
            default:
                if ((matcher = PATTERN.matcher(message)).matches()) {
                    String phoneNumber = matcher.group(1);
                    String name = matcher.group(2);
                    catShelterService.saveCatShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);
                    SendMessage sendMessage = new SendMessage(chatId, "Ваши данные успешно сохранены!");
                    telegramBot.execute(sendMessage);
                }
        }
    }

    public void catShelterReportMenu(Update update) {
        if (BACK_TO_MENU_SHELTER.equals(update.message().text())) {
            catShelterService.catShelterMenu(update.message().chat().id());
            shelterService.saveClient(update.message().chat().id(), Step.CAT_SHELTER_MENU);
        } else {
            if (update.message().text() != null) {
                catShelterService.saveStringReport(update);
            } else if (update.message().photo() != null){
                catShelterService.savePhotoReport(update);
            }
        }
    }
}
