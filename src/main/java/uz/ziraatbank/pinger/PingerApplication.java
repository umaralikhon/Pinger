package uz.ziraatbank.pinger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.ziraatbank.pinger.telegram.TelegaConfig;
import java.io.IOException;

/**
 * @Aauthor Umaralikhon Kayumov
 */

@SpringBootApplication
@EnableScheduling
public class PingerApplication {

    public static void main(String[] args){
        SpringApplication.run(PingerApplication.class, args);
    }
}
