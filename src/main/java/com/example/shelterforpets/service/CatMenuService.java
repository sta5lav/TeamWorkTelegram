package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.Step;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            case "Узнать информацию о приюте":
                catShelterService.catInfoShelterMenu(chatId);
                shelterService.saveClient(chatId, Step.CAT_SHELTER_INFO_MENU);
                break;
            case "Как взять животное из приюта":
                catShelterService.catAdoptionInstructions(chatId);
                break;
            case "Прислать отчет о питомце":
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
            case "О приюте":
                catShelterService.sendCatShelterInfo(chatId);
                break;
            case "Расписание работы приюта и адрес, схема проезда":
                catShelterService.scheduleCatShelter(chatId);
                break;
            case "Контактные данные охраны для оформления пропуска на машину":
                catShelterService.securityContactDetailsCatShelter(chatId);
                break;
            case "Общие рекомендации о технике безопасности на территории приюта":
                catShelterService.recommendationInTheCatShelter(chatId);
                break;
            case "Принять и записать контактные данные для связи":
                catShelterService.nameAndPhoneNumberPattern(chatId);
                break;
            case "Позвать волонтера":
                catShelterService.getHelpingCatShelterVolunteers(chatId);
                break;
            case "Вернуться в меню приюта":
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
