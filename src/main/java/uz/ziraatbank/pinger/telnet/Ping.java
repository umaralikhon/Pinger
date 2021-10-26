package uz.ziraatbank.pinger.telnet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.ziraatbank.pinger.entity.*;
import uz.ziraatbank.pinger.service.*;
import uz.ziraatbank.pinger.telegram.TelegaMsgSender;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Component
public class Ping {
    private final PortsService portsService;

    @Autowired
    public Ping(PortsService portsService){
        this.portsService = portsService;
    }

    private String logId;
    private static final Logger LOG = Logger.getLogger(Ping.class);
    private List<Ports> portsList = new ArrayList<>();
    public String message = "";
    private TelegaMsgSender telega;

    @Scheduled(fixedRate = 5000)
    public void pingPorts() throws IOException {
        String serviceEmoji = "\uD83D\uDCCC";
        String statusEmoji = "\uD83D\uDEA9";
        String upEmoji = "✅";
        String dateEmoji = "\uD83D\uDCC5";
        String logIdEmoji = "\uD83D\uDD0D";
        String descEmoji = "\uD83D\uDCDD";
        String downEmoji = "❌";

        portsList = portsService.getAll();

        for (Ports p : portsList) {
            String date = LocalDate.now().toString();
            String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            telega = new TelegaMsgSender();
            logId = UUID.randomUUID().toString();

            if (!p.getHandOff()) {
                try (Socket socket = new Socket(p.getHost(), p.getPort())) {
                    System.out.println("Success " + p.getServices().getServiceName() + " || " + p.getSubservice());

                    //Тут %0A == \n
                    if (!p.getActive() && socket.isConnected()) {
                        message = serviceEmoji + "Service: " + p.getServices().getServiceName() + "%0A" +
                                statusEmoji + "Status: Up " + upEmoji + "%0A" +
                                dateEmoji + "Date: " + date + " " + time + "%0A" +
                                logIdEmoji + "LogID: " + logId + "%0A" +
                                descEmoji + "Desc: " + p.getHost() + " STARTED at port " + p.getPort();

                        p.setCounter(0);
                        telega.setUrl(message);
                    }

                    p.setActive(true);
                    portsService.save(p);
                    LOG.info(logId + ": SUCCESS: " + p.getServices().getServiceName() + " || " + p.getPort() + " || " + p.getHost());

                } catch (ConnectException ex) {
                    int count = p.getCounter();
                    count++;
                    p.setCounter(count);

                    if (p.getCounter() == 3) {
                        p.setActive(false);

                        message = serviceEmoji + "Service: " + p.getServices().getServiceName() + "%0A" +
                                statusEmoji + "Status: Down " + downEmoji + "%0A" +
                                dateEmoji + "Date: " + date + " " + time + "%0A" +
                                logIdEmoji + "LogID: " + logId + "%0A" +
                                descEmoji + "Desc: " + p.getHost() + " DOWNED at port " + p.getPort();

                        telega.setUrl(message);
                    }

                    if (p.getCounter() >= 3) {
                        p.setActive(false);
                    }

                    portsService.save(p);
                    ex.getMessage();
                    LOG.error(logId + ": ERROR: " + p.getServices().getServiceName() + " || " + p.getPort() + " || " +
                            p.getHost() + " || " + ex.getMessage());
                }
            }
        }
    }
}
