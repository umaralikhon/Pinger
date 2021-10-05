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
//    int count = 0;

    //TODO: this is working code!
    //TODO: Should to check reconnection part!
    @Scheduled(fixedRate = 5000)
    public void pingPorts() throws IOException {
        portsList = portsService.getAll();

        for (Ports p : portsList) {
            Date date = new Date();
            telega = new TelegaMsgSender();

            try (Socket socket = new Socket(p.getIp(), p.getPort())) {
                System.out.println("Success " + p.getServices().getServiceName() + " || " + p.getSubservice());

                if (p.getActive() == false && socket.isConnected()) {
                    message = "Server is connected: " +
                            " Service: " + p.getServices().getServiceName() + " || " +
                            " Subservice: " + p.getSubservice() + " || " +
                            " Ip: " + p.getIp() + " || " +
                            " Port: " + p.getPort() + " || " +
                            " Time: " + date;
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
                    message = "Server disconnected! " + p.getServices().getServiceName() + " || " +
                            " Subservice: " + p.getSubservice() + " || " +
                            " Ip: " + p.getIp() + " || " +
                            " Port: " + p.getPort() + " || " +
                            " Time: " + date;
                    telega.setUrl(message);
                }

                //Не трогать! Не зняю почему, но это нужная часть кода!
                if(p.getCounter() >= 3){
                    p.setActive(false);
                }

                System.out.println(count);//Debug

                portsService.save(p);
                ex.getMessage();
            }
            catch (SocketException ex) {
                int count = p.getCounter();
                count++;
                p.setCounter(count);

                if (p.getCounter() == 3) {
                    p.setActive(false);
                    message = "Server disconnected! " + p.getServices().getServiceName() + " || " +
                            " Subservice: " + p.getSubservice() + " || " +
                            " Ip: " + p.getIp() + " || " +
                            " Port: " + p.getPort() + " || " +
                            " Time: " + date;
                    telega.setUrl(message);
                }

                //Не трогать! Не зняю почему, но это нужная часть кода!
                if(p.getCounter() >= 3){
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
