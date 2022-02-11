package uz.ziraatbank.pinger.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class TelegaMessageRequestDto {

    @JsonProperty("chat_id")
    private Long chatId;

    @JsonProperty("text")
    private String text;
}
