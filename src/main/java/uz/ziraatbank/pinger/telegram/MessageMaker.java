package uz.ziraatbank.pinger.telegram;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ziraatbank.pinger.config.Status;
import uz.ziraatbank.pinger.config.TelegaProperties;

@Service
@AllArgsConstructor
public class MessageMaker {

    private final TelegaProperties telegaProps;
    private final TelegaService telegaService;

    public void sendMessage(String serviceName, String time, String host, Integer port, Status status) {

        TelegaMessageRequestDto request = new TelegaMessageRequestDto();

        String baseMessage = "" +
                "\uD83D\uDCCCService: %s\n" +
                "\uD83D\uDEA9Status:   %s %s\n" +
                "\uD83D\uDCC5Date:      %s\n" +
                "\uD83D\uDCDDDesc:      %s %s at port %s";

        if (status.equals(Status.UP)) {
            String text = String.format(baseMessage, serviceName, status, "✅", time, host, "START", port);
            request.setChatId(Long.valueOf(telegaProps.getChatId()));
            request.setText(text);
            telegaService.sendMessage(request);
        } else {
            String text = String.format(baseMessage, serviceName, status, "❌", time, host, "DOWN", port);
            request.setChatId(Long.valueOf(telegaProps.getChatId()));
            request.setText(text);
            telegaService.sendMessage(request);
        }
    }
}
