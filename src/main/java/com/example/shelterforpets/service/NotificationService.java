package com.example.shelterforpets.service;

import com.example.shelterforpets.listener.TelegramBotUpdatesListener;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private TelegramBot telegramBot;

    public NotificationService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId The ID of the chat to send the notification to.
     * @param text   The text of the notification message.
     */
    public void sendNotification(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    public void sendNotificationWithMenu(long chatId, String text, Keyboard keyboard) {
        SendMessage message = new SendMessage(chatId, text);
        message.replyMarkup(keyboard);
        telegramBot.execute(message);
    }
}
