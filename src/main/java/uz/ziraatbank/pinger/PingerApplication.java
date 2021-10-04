package uz.ziraatbank.pinger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.ziraatbank.pinger.telegram.TelegaConfig;
//import uz.ziraatbank.pinger.telegram.TelegaConfig;
import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class PingerApplication {

    public static void main(String[] args) throws TelegramApiException, IOException {
//        TelegaConfig myBot = new TelegaConfig();
        TelegramBotsApi telegramBot = new TelegramBotsApi(DefaultBotSession.class);

//        try {
//            telegramBot.registerBot(myBot);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        SpringApplication.run(PingerApplication.class, args);
    }
}
