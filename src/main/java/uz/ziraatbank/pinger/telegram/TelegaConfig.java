package uz.ziraatbank.pinger.telegram;

import lombok.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.ziraatbank.pinger.telnet.Ping;

@Data
public final class TelegaConfig extends TelegramLongPollingBot {
    private final String BOTNAME="@pingerrbot";
    private final String TOKEN="2012688061:AAGAKCuvyjWB3J2LG4WS8zpN8FAhI4KzVwU";
    private Ping ping = new Ping();

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOTNAME;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update){
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();


        sendMessage.setChatId(String.valueOf(chatId));

        if(update.hasMessage()){
            sendMessage.setText("Hello world");

            try{
                execute(sendMessage);
            }catch(TelegramApiException ex){
                ex.getMessage();
            }
        }
    }
}
