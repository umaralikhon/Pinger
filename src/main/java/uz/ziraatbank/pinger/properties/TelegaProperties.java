package uz.ziraatbank.pinger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
@ConfigurationProperties(prefix = "telega")
@Data
public class TelegaProperties {

    private String token;
    private String name;
    private List<String> chatIds;
}
