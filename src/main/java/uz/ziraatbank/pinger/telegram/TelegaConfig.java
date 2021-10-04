package uz.ziraatbank.pinger.telegram;

import com.vdurmont.emoji.EmojiParser;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.ziraatbank.pinger.entity.Users;
import uz.ziraatbank.pinger.service.UsersService;

@Data
@NoArgsConstructor
public final class TelegaConfig extends TelegramLongPollingBot {
    private final String BOTNAME = "@pingerrbot";
    private final String TOKEN = "2012688061:AAGAKCuvyjWB3J2LG4WS8zpN8FAhI4KzVwU";
    private UsersService usersService;
    private Users user = new Users();
    Long chatId = null;


    @Autowired
    public TelegaConfig(UsersService usersService) {
        this.usersService = usersService;
    }

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

            if (text.equals("/start")) {
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                sendMessage.setText("You subscribed, so You will get error messages " + emoji);

                try {
                    execute(sendMessage);
                } catch (TelegramApiException ex) {
                    ex.printStackTrace();
                }
            }
//            user.setChatId(String.valueOf(chatId));
//            usersService.save(user);
        }
    }
}

