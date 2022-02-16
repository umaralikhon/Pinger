package uz.ziraatbank.pinger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "props")
@Data
public class Properties {

    private String token;
    private String botName;
    private String baseQueryUrl;
    private String baseMethodUrl;
    private String chatId;
    private String source; //Определяет на каком сервере поднят сервис
}
