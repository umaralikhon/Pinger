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
    public String message;
    private TelegaMsgSender telega;

    //TODO: this is working code!
    //TODO: Should to check reconnection part!
    @Scheduled(fixedRate = 3000)
    public void pingPorts() throws IOException {

        portsList = portsService.getAll();

        for (Ports p : portsList) {
            Date date = new Date();

            telega = new TelegaMsgSender();
            try (Socket socket = new Socket(p.getIp(), p.getPort())) {
                System.out.println("Success " + p.getServices().getServiceName() + " || " + p.getSubservice());

                if (p.getServices().getActive() == false && socket.isConnected()) {
                    message = "Server is connected";
                    telega.setText(message);
                    telega.setUrlFormat();
                    telega.setUrl();
                }

                p.getServices().setActive(true);
                portsService.save(p);
            } catch (ConnectException ex) {
                message = "Error " + p.getServices().getServiceName() + " || " +
                        " Ip: " + p.getIp() + " || " +
                        " Port: " + p.getPort() + " || " +
                        " Time: " + date;

                telega.setText(message);
                telega.setUrlFormat();
                telega.setUrl();

                p.getServices().setActive(false);
                portsService.save(p);

                ex.getMessage();

            } catch (SocketException ex) {
                message = "Error " + p.getServices().getServiceName() +
                        "Ip: " + p.getIp() + " || " +
                        "Port: " + p.getPort() + " || " +
                        "Time: " + date;

                p.getServices().setActive(false);
                portsService.save(p);

                telega.setText(message);
                telega.setUrlFormat();
                telega.setUrl();

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
