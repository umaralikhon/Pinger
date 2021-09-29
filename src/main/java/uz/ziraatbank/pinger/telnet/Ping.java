package uz.ziraatbank.pinger.telnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.ziraatbank.pinger.entity.*;
import uz.ziraatbank.pinger.service.*;
import java.io.IOException;
import java.net.*;
import java.util.*;

@Component
public class Ping {
    @Autowired
    private PortsService portsService;

    @Autowired
    private ServicesService servicesService;

    private Ports ports = null;
    private List<Ports>portsList = new ArrayList<>();

    private List<Services> serviceList;
    private String msg = null;

    @Scheduled(fixedRate = 5000)
    public String pingPorts() throws IOException {

        portsList = portsService.getAll();

        for (Ports p : portsList) {
            try (Socket socket = new Socket(p.getIp(), p.getPort())) {
                msg = "Success " + p.getServices().getServiceName() + " " + p.getSubservice();
                System.out.println("Success " + p.getServices().getServiceName() + " " + p.getSubservice());
            } catch (ConnectException ex) {
                msg = "Error " + p.getServices().getServiceName() + " || " + p.getSubservice();
                System.out.println(msg);
                ex.getMessage();
            } catch (IllegalArgumentException ex) {
                msg = "Error " + p.getServices().getServiceName() + " || " + p.getSubservice();
                System.out.println(msg);
                ex.getMessage();
            } catch (SocketException ex) {
                msg = "Error " + p.getServices().getServiceName() + " || " + p.getSubservice();
                System.out.println(msg);
                ex.getMessage();
            }
        }

        System.out.println("====================================");
        return msg;
    }
}
