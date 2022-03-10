package example.pinger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @Aauthor Umaralikhon Kayumov
 */

@SpringBootApplication
@EnableScheduling
public class PingerApplication {

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(PingerApplication.class, args);
    }
}
