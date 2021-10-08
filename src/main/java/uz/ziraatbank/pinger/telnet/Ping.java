package uz.ziraatbank.pinger.telnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.ziraatbank.pinger.entity.*;
import uz.ziraatbank.pinger.service.*;
import uz.ziraatbank.pinger.telegram.TelegaMsgSender;
import java.io.*;
import java.net.*;
import java.util.*;

@Component
public class Ping {
    @Autowired
    private PortsService portsService;

    @Autowired
    private ServicesService servicesService;

    private Ports ports = null;
    private List<Ports> portsList = new ArrayList<>();
    private int num = 1;
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
            Date date = new Date();
            telega = new TelegaMsgSender();

            try (Socket socket = new Socket(p.getHost(), p.getPort())) {
                System.out.println("Success " + p.getServices().getServiceName() + " || " + p.getSubservice());

                //Тут %0A == \n
                if (p.getActive() == false && socket.isConnected()) {
                    message = serviceEmoji + "Service: " + p.getServices().getServiceName() + "%0A" +
                                    statusEmoji + "Status: Up " + upEmoji + "%0A" +
                                    dateEmoji + "Date: " + date + "%0A" +
                                    logIdEmoji + "LogID: " + "There will be Log ID" + "%0A" +
                                    descEmoji + "Desc: " + p.getHost() + " started at port " + p.getPort();

                    p.setCounter(0);
                    telega.setUrl(message);
                }

                p.setActive(true);
                portsService.save(p);

            } catch (ConnectException ex) {
                int count = p.getCounter();
                count++;
                p.setCounter(count);

                if (p.getCounter() == 3) {
                    p.setActive(false);

                    message = serviceEmoji + "Service: " + p.getServices().getServiceName() + "%0A" +
                            statusEmoji + "Status: Down " + downEmoji + "%0A" +
                            dateEmoji + "Date: " + date + "%0A" +
                            logIdEmoji + "LogID: " + "There will be Log ID" + "%0A" +
                            descEmoji + "Desc: " + p.getHost() + " downed at port " + p.getPort();

                    telega.setUrl(message);
                }

                if (p.getCounter() >= 3) {
                    p.setActive(false);
                }

                System.out.println(count);//Debug

                portsService.save(p);
                ex.getMessage();
            }

            try (FileWriter fw = new FileWriter("Logger.txt", true)) {

                if (message != null) {
                    fw.write(num + ". " + message + " \t\t|| " + date);
                    fw.append('\n');
                    fw.flush();
                }
            } catch (IOException ex) {
                ex.getCause();
            }
            num++;
        }
    }
}
