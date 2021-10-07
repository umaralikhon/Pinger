package uz.ziraatbank.pinger.telnet;

import com.vdurmont.emoji.EmojiParser;
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

    //TODO: this is working code!
    @Scheduled(fixedRate = 5000)
    public void pingPorts() throws IOException {
        portsList = portsService.getAll();
        String screamEmoji = "\uD83D\uDE31";
        String serviceEmoji = "\uD83C\uDF10";
        String subServiceEmoji = "\uD83E\uDE99";
        String hostEmoji = "\uD83D\uDD17";
        String portEmoji = "\uD83D\uDCE5";
        String timeEmoji = "\uD83D\uDD54";
        String connectEmoji = "\uD83D\uDE0E";


        for (Ports p : portsList) {
            Date date = new Date();
            telega = new TelegaMsgSender();

            try (Socket socket = new Socket(p.getHost(), p.getPort())) {
                System.out.println("Success " + p.getServices().getServiceName() + " || " + p.getSubservice());

                //Тут %0A == \n
                if (p.getActive() == false && socket.isConnected()) {
                    message =
                            connectEmoji + "The server is up" + connectEmoji + "%0A" +
                            serviceEmoji + "Service: " + p.getServices().getServiceName() + "%0A" +
                                    subServiceEmoji + "Subservice: " + p.getSubservice() + "%0A" +
                            hostEmoji + "Host: " + p.getHost() + "%0A" +
                            portEmoji + "Port: " + p.getPort() + "%0A" +
                            timeEmoji + "Time: " + date;
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
                    message =
                            screamEmoji + "The server crashed" + screamEmoji + "%0A"+
                            serviceEmoji + "Server: " + p.getServices().getServiceName() + "%0A" +
                            subServiceEmoji + "Subservice: " + p.getSubservice() + "%0A" +
                            hostEmoji + "Host: " + p.getHost() + "%0A" +
                            portEmoji + "Port: " + p.getPort() + "%0A" +
                            timeEmoji + "Time: " + date;

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
                    message =
                            screamEmoji + "The server crashed" + screamEmoji +"%0A"+
                            serviceEmoji + "Server: " + p.getServices().getServiceName() + "%0A" +
                            subServiceEmoji + " Subservice: " + p.getSubservice() + "%0A" +
                            hostEmoji + "Host: " + p.getHost() + "%0A" +
                            portEmoji + "Port: " + p.getPort() + "%0A" +
                            timeEmoji + "Time: " + date;
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
