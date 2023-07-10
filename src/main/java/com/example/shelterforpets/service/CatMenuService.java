package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.Step;
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
                break;
            case SEND_A_PET_REPORT:
                catShelterService.report(chatId);
                break;
            default:
                catShelterService.messageHelpingVolunteers(chatId, firstName, userName);
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
}
