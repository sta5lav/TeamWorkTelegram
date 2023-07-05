package com.example.shelterforpets.listener;

import com.example.shelterforpets.service.MessageService;
import com.example.shelterforpets.service.firstStage.FirstStageService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service

public class TelegramBotUpdatesListener implements UpdatesListener {
    // создаем поле логгер для передачи логов в консоль
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private static final Pattern PATTERN = Pattern.compile("(\\d+) ([А-я\\d.,!?:\\s]+)");

    private final TelegramBot telegramBot;
    private final MessageService messageService;
    private final FirstStageService firstStageService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      MessageService messageService,
                                      FirstStageService firstStageService) {
        this.telegramBot = telegramBot;
        this.messageService = messageService;
        this.firstStageService = firstStageService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Processes the updates received from the Telegram Bot API.
     *
     * @param updates The list of updates to process.
     * @return The status of the updates processing.
     */
    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing update: {}", update);
                String userName = update.message().chat().username();
                String firstName = update.message().chat().firstName();
                long chatId = update.message().chat().id();
                String message = update.message().text();
                if (update.message().text() == null) {
                    logger.warn("Skip message because text is null");
                    return;
                }
                // Process your updates here
                if (message.equals("/start")) {
                    messageService.sendWelcomeMessage(chatId);
                //выбор приюта
                } else if (message.equals("Приют для кошек")) {
                    messageService.sendInfoShelterMenu(chatId);
                    firstStageService.saveCatShelterForClient(chatId, "catShelterMenu");
                } else if (message.equals("Приют для собак")) {
                    messageService.sendInfoShelterMenu(chatId);
                    firstStageService.saveDogShelterForClient(chatId, "dogShelterMenu");
                } else if (message.equals("Узнать информацию о приюте") ||
                        message.equals("Вернуться в меню информации о приюте")) {
                    firstStageService.infoShelterMenu(chatId);
                } else if (firstStageService.getStepClient(chatId).equals("dogShelterMenu")) {
                    dogShelterMenu(update);
                } else if (firstStageService.getStepClient(chatId).equals("catShelterMenu")) {
                    catShelterMenu(update);

                } else if (message.equals("Как взять животное из приюта")) {
                    messageService.sendAnimalAdoptionInstructions(chatId);
                } else if (message.equals("Прислать отчет о питомце")) {
                    messageService.sendPetReport(chatId);


                } else {
                    messageService.sendMessageHelpingVolunteers(chatId, firstName, userName);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    //Методы для работы с меню информации о приюте
    private void catShelterMenu(Update update) {
        long chatId = update.message().chat().id();
        String message = update.message().text();
        Matcher matcher;

        if (message.equals("Рассказать о приюте")) {
            firstStageService.sendCatShelterInfo(chatId);
        } else if (message.equals("Выдать расписание работы приюта и адрес, схему проезда")) {
            firstStageService.scheduleCatShelter(chatId);
        } else if (message.equals("Выдать контактные данные охраны для оформления пропуска на машину")) {
            firstStageService.securityContactDetailsCatShelter(chatId);
        } else if (message.equals("Выдать общие рекомендации о технике безопасности на территории приюта")) {
            firstStageService.recommendationInTheCatShelter(chatId);
        }  else if (message.equals("Принять и записать контактные данные для связи.")) {
            firstStageService.saveClientInCatShelter(chatId);
        } else if (message.equals("Позвать волонтера")) {
            firstStageService.getHelpingCatShelterVolunteers(chatId);
        } else if (message != null && (matcher = PATTERN.matcher(message)).matches()) {
            String phoneNumber = matcher.group(1);
            String name = matcher.group(2);
            firstStageService.saveCatShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);
            SendMessage sendMessage = new SendMessage(chatId, "Ваши данные успешно сохранены!");
            telegramBot.execute(sendMessage);
        }
    }

    private void dogShelterMenu(Update update) {
        long chatId = update.message().chat().id();
        String message = update.message().text();
        Matcher matcher;
        if (message.equals("/info")) {
            firstStageService.sendDogShelterInfo(chatId);
        } else if (message.equals("/go")) {
            firstStageService.scheduleDogShelter(chatId);
        } else if (message.equals("/securityDetails")) {
            firstStageService.securityContactDetailsDogShelter(chatId);
        } else if (message.equals("/tb")) {
            firstStageService.recommendationInTheDogShelter(chatId);
        } else if (message.equals("/saveMyClientCard")) {
            firstStageService.saveClientInDogShelter(chatId);
        } else if (message.equals("/volunteer")) {
            firstStageService.getHelpingDogShelterVolunteers(chatId);
        } else if (message != null && (matcher = PATTERN.matcher(message)).matches()) {
            String phoneNumber = matcher.group(1);
            String name = matcher.group(2);
            firstStageService.saveDogShelterClientNameAndPhoneNumber(chatId, name, phoneNumber);
            SendMessage sendMessage = new SendMessage(chatId, "Ваши данные успешно сохранены!");
            telegramBot.execute(sendMessage);
        }
    }
}
