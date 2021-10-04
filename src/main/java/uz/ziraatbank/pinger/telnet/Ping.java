package uz.ziraatbank.pinger.telnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.ziraatbank.pinger.entity.*;
import uz.ziraatbank.pinger.service.*;
import uz.ziraatbank.pinger.telegram.TelegaMsgSender;
//import uz.ziraatbank.pinger.telegram.TelegaConfig;

import java.io.*;
import java.net.*;
import java.util.*;

@Component
public class Ping {
    @Autowired
    private PortsService portsService;
//    private ErrorMessages errors;

    @Autowired
    private ServicesService servicesService;

    private Ports ports = null;
    private List<Ports> portsList = new ArrayList<>();
    private int num = 1;
    public String message;
    private TelegaMsgSender telega = new TelegaMsgSender();

    //TODO: this is working code!
    @Scheduled(fixedRate = 3000)
    public void pingPorts() throws IOException {

        portsList = portsService.getAll();

        for (Ports p : portsList) {
            try (Socket socket = new Socket(p.getIp(), p.getPort())) {
                System.out.println("Success " + p.getServices().getServiceName() + " || " + p.getSubservice());

                p.getServices().setActive(true);
                portsService.save(p);
            } catch (ConnectException ex) {
                message = "Error " + p.getServices().getServiceName() + " \t\t|| " + p.getIp() + "\t\t|| " +
                        p.getPort() + "\t\t|| " + ErrorMessages.LOW_INET.getName() + " OR " + ErrorMessages.INVALID_PORT.getName();

                telega.setText(message);
                telega.setUrlFormat();
                telega.setUrl();

                p.getServices().setActive(false);
                portsService.save(p);

                System.out.println("Server is disconnected 0!");
                System.out.println(message);
                ex.getMessage();

            } catch (SocketException ex) {
                message = "Error " + p.getServices().getServiceName() + " \t\t|| " + p.getSubservice() + "\t\t|| " +
                        ErrorMessages.NET_CONNECT.getName() + " OR " + ErrorMessages.LOW_INET.getName();
                p.getServices().setActive(false);
                portsService.save(p);

                telega.setText(message);
                telega.setUrlFormat();
                telega.setUrl();

                System.out.println("Server is disconnected 2!");
                System.out.println(message);
                ex.getMessage();
            }

            try (FileWriter fw = new FileWriter("Logger.txt", true)) {
                Date date = new Date();

                if(message!=null) {
                    fw.write(num + ". " + message + " \t\t|| " + date);
                    fw.append('\n');
                    fw.flush();
                }
            } catch (IOException ex) {
                ex.getCause();
            }
            num++;
        }

        System.out.println("====================================");
    }
}
