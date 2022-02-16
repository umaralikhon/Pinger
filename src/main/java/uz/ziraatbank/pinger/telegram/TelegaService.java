package uz.ziraatbank.pinger.telegram;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.ziraatbank.pinger.config.Properties;

@Service
@AllArgsConstructor
@Data
public class TelegaService {

    private final Properties properties;
    private final RestTemplate restTemplate;

    public TelegaMessageResponseDto sendMessage(TelegaMessageRequestDto request) {
        return restTemplate.exchange(
                String.format(properties.getBaseMethodUrl(), properties.getToken(), "sendMessage"),
                HttpMethod.POST,
                new HttpEntity<>(request),
                TelegaMessageResponseDto.class
        ).getBody();
    }
}
