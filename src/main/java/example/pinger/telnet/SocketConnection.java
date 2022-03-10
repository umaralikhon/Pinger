package example.pinger.telnet;

import example.pinger.model.entity.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SocketConnection {

    public Status tryConnect(String host, Integer port) {
        try {
            new Socket(host, port);
            return Status.UP;
        } catch (IOException e) {
            e.getMessage();
            log.error(e.getMessage());
            return Status.DOWN;
        }
    }
}
