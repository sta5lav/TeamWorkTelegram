package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.Step;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            case "Узнать информацию о приюте":
                dogShelterService.dogInfoShelterMenu(chatId);
                shelterService.saveClient(chatId, Step.DOG_SHELTER_INFO_MENU);
                break;
            case "Как взять животное из приюта":
                dogShelterService.dogAdoptionInstructions(chatId);
                break;
            case "Прислать отчет о питомце":
                dogShelterService.report(chatId);
                break;
            default:
                dogShelterService.messageHelpingVolunteers(chatId, firstName, userName);
        }
    }

    public void dogShelterInfoMenu(long chatId, String message) {
        Matcher matcher;

        switch (message) {
            case "О приюте":
                dogShelterService.sendDogShelterInfo(chatId);
                break;
            case "Расписание работы приюта и адрес, схема проезда":
                dogShelterService.scheduleDogShelter(chatId);
                break;
            case "Контактные данные охраны для оформления пропуска на машину":
                dogShelterService.securityContactDetailsDogShelter(chatId);
                break;
            case "Общие рекомендации о технике безопасности на территории приюта":
                dogShelterService.recommendationInTheDogShelter(chatId);
                break;
            case "Принять и записать контактные данные для связи":
                dogShelterService.nameAndPhoneNumberPattern(chatId);
                break;
            //case "Позвать волонтера":
                //dogShelterService.getHelpingDogShelterVolunteers(chatId);
                //break;
            case "Вернуться в меню приюта":
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
