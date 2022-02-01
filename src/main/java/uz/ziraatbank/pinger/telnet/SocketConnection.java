package uz.ziraatbank.pinger.telnet;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ziraatbank.pinger.config.Status;
import java.io.IOException;
import java.net.Socket;
import static uz.ziraatbank.pinger.config.Status.DOWN;
import static uz.ziraatbank.pinger.config.Status.UP;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SocketConnection {

    public Status tryConnect(String host, Integer port) {
        try {
            new Socket(host, port);
            return UP;
        } catch (IOException e) {
            e.getMessage();
            return DOWN;
        }
    }
}
