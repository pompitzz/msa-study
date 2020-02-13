package me.sun.springbootstudy.telegram;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.TelegramInfo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramPollingBot extends TelegramLongPollingBot {


    private final TelegramInfo telegramInfo;

    public void sendMessage(String message) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage()
                .setChatId(telegramInfo.getChatId())
                .setText(message);
        execute(sendMessage);
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return telegramInfo.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return telegramInfo.getBotToken();
    }
}
