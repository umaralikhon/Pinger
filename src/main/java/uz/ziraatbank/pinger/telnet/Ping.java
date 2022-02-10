package uz.ziraatbank.pinger.telnet;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.ziraatbank.pinger.config.Status;
import uz.ziraatbank.pinger.model.entity.*;
import uz.ziraatbank.pinger.model.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static uz.ziraatbank.pinger.config.Status.*;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class Ping {
    private final PortsService portsService;
    private final SocketConnection connection;
    private final DownHistoryService historyService;
    private List<Ports> portsList;

    @Scheduled(fixedRateString = "PT1M")
    public void pingPorts() throws InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        portsList = portsService.findAll();

        for (Ports p : portsList) {
            int attempt = p.getAttempt();

            if (p.getStatus() != OFF) {

                Long start = System.currentTimeMillis();
                Status connect = connection.tryConnect(p.getHost(), p.getPort());
                Long end = System.currentTimeMillis();
                Double timeout = Double.valueOf(end - start) / 1000;

                if (connect == DOWN) {
                    PingTime pingTime = new PingTime();
                    pingTime.setTime(LocalDateTime.now());
                    pingTime.setTimeout(timeout);

                    p.setAttempt(attempt + 1);
                    p.setStatus(DOWN);
                    p.setLastTimeout(timeout);
                    p.addPingTimeToPorts(pingTime);

                    if (!p.getRegistered()) {
                        p.setRegistered(true);
                        historyService.saveItems(p.getServiceName(), p.getHost(), p.getPort(), LocalDateTime.now().format(formatter), DOWN);
                    }
                    portsService.save(p);

                } else if (connect == UP) {
                    PingTime pingTime = new PingTime();
                    pingTime.setTime(LocalDateTime.now());
                    pingTime.setTimeout(timeout);

                    p.setAttempt(0);
                    p.setStatus(UP);
                    p.setLastTimeout(timeout);
                    p.addPingTimeToPorts(pingTime);

                    if (p.getRegistered()) {
                        p.setRegistered(false);
                        historyService.saveItems(p.getServiceName(), p.getHost(), p.getPort(), LocalDateTime.now().format(formatter), UP);
                    }
                    portsService.save(p);
                }
            }

            Thread.sleep(5000);
        }
    }
}
