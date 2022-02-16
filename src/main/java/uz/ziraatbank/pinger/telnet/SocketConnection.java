package uz.ziraatbank.pinger.telnet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ziraatbank.pinger.model.entity.Status;

import java.io.IOException;
import java.net.Socket;

import static uz.ziraatbank.pinger.model.entity.Status.DOWN;
import static uz.ziraatbank.pinger.model.entity.Status.UP;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SocketConnection {

    public Status tryConnect(String host, Integer port) {
        try {
            new Socket(host, port);
            return UP;
        } catch (IOException e) {
            e.getMessage();
            log.error(e.getMessage());
            return DOWN;
        }
    }
}
