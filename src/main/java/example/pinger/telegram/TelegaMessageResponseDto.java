package example.pinger.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegaMessageResponseDto {

    @JsonProperty("message_id")
    private Integer messageId;

    @JsonProperty("text")
    private String text;
}
