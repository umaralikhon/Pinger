package uz.ziraatbank.pinger.telegram;

import com.vdurmont.emoji.EmojiParser;
import lombok.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.ziraatbank.pinger.model.entity.Users;

@Data
@NoArgsConstructor
public final class TelegaConfig extends TelegramLongPollingBot {
    private final String BOTNAME = "@pingerrbot";
    private final String TOKEN = "2012688061:AAGAKCuvyjWB3J2LG4WS8zpN8FAhI4KzVwU";
    private Users user = new Users();
    Long chatId = null;

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOTNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
            SendMessage sendMessage = new SendMessage();
            String text = update.getMessage().getText();
            String emoji = EmojiParser.parseToUnicode(":stuck_out_tongue_winking_eye:");

            if (text.equals("/chatid")) {
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                sendMessage.setText("Chat ID: " + chatId + emoji);

                try {
                    execute(sendMessage);
                } catch (TelegramApiException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

