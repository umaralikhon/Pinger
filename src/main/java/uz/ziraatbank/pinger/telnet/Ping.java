package uz.ziraatbank.pinger.telnet;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.ziraatbank.pinger.config.Status;
import uz.ziraatbank.pinger.model.entity.*;
import uz.ziraatbank.pinger.model.service.*;
import java.util.*;
import static uz.ziraatbank.pinger.config.Status.*;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class Ping {
    private final PortsService portsService;
    private final SocketConnection connection;
    private List<Ports> portsList;

    @Scheduled(fixedRate = 5000)
    public void pingPorts() {
        portsList = portsService.getAll();
        for (Ports p : portsList) {
            int attempt = p.getAttempt();

            if (p.getStatus() != OFF) {
                Status connect = connection.tryConnect(p.getHost(), p.getPort());

                if (connect == DOWN) {
                    p.setAttempt(attempt + 1);
                    p.setStatus(DOWN);
                    portsService.save(p);

                    System.out.println(p.getServiceName() + " " + p.getHost() + " " + p.getPort() + " " + "DOWN");

                } else if (connect == UP) {
                    p.setAttempt(0);
                    p.setStatus(UP);
                    portsService.save(p);

                    System.out.println(p.getServiceName() + " " + p.getHost() + " " + p.getPort() + " " + "UP");
                }
            }
        }
    }
}
