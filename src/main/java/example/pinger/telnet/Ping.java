package example.pinger.telnet;

import example.pinger.model.entity.PingTime;
import example.pinger.model.entity.Ports;
import example.pinger.model.entity.Status;
import example.pinger.model.service.DownHistoryService;
import example.pinger.model.service.PortsService;
import example.pinger.telegram.MessageMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import example.pinger.config.Properties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class Ping {
    private final PortsService portsService;
    private final SocketConnection connection;
    private final DownHistoryService historyService;
    private final MessageMaker messageMaker;
    private List<Ports> portsList;
    private final Properties properties;

    @Scheduled(fixedRateString = "PT5M")
    public void pingPorts() throws InterruptedException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        portsList = portsService.findAll();

        for (Ports p : portsList) {
            int attempt = p.getAttempt();

            if (p.getSource().name().equalsIgnoreCase(properties.getSource())) {

                if (p.getStatus() != Status.OFF) {

                    Long start = System.currentTimeMillis();
                    Status connect = connection.tryConnect(p.getHost(), p.getPort());
                    Long end = System.currentTimeMillis();
                    Double timeout = Double.valueOf(end - start) / 1000;

                    if (connect == Status.DOWN) {
                        PingTime pingTime = new PingTime();
                        pingTime.setTime(LocalDateTime.now());
                        pingTime.setTimeout(timeout);

                        p.setAttempt(attempt + 1);
                        p.setStatus(Status.DOWN);
                        p.setLastTimeout(timeout);
                        p.addPingTimeToPorts(pingTime);


                        if (!p.getRegistered()) {
                            p.setRegistered(true);
                            historyService.saveItems(p.getServiceName(), p.getHost(), p.getPort(), LocalDateTime.now().format(formatter), Status.DOWN);
                            messageMaker.sendMessage(p.getServiceName(), LocalDateTime.now().format(formatter), p.getHost(), p.getPort(), Status.DOWN);
                        }
                        portsService.save(p);

                    } else if (connect == Status.UP) {
                        PingTime pingTime = new PingTime();
                        pingTime.setTime(LocalDateTime.now());
                        pingTime.setTimeout(timeout);

                        p.setAttempt(0);
                        p.setStatus(Status.UP);
                        p.setLastTimeout(timeout);
                        p.addPingTimeToPorts(pingTime);

                        if (p.getRegistered()) {
                            p.setRegistered(false);
                            historyService.saveItems(p.getServiceName(), p.getHost(), p.getPort(), LocalDateTime.now().format(formatter), Status.UP);
                            messageMaker.sendMessage(p.getServiceName(), LocalDateTime.now().format(formatter), p.getHost(), p.getPort(), Status.UP);
                        }
                        portsService.save(p);
                    }
                }

                Thread.sleep(5000);
            }
        }
    }
}
