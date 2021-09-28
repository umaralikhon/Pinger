package uz.ziraatbank.pinger.telnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.ziraatbank.pinger.entity.Ports;
import uz.ziraatbank.pinger.service.PortsService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

@Component
public class Ping {
    @Autowired
    private PortsService portsService;
    Ports ports = null;
    List<Ports> portsList;

//    @Autowired
//    public Ping(PortsService portsService) {
//        this.portsService = portsService;
//    }

//    public void serializeData(){
//        portsService.findAll();
//    }

//    @Scheduled(fixedRate = 3000)
//    public static boolean pingHost(String host, int port) {
//        try (Socket socket = new Socket()) {
//            socket.connect(new InetSocketAddress(host, port));
//            return true;
//        } catch (IOException e) {
//            return false; // Either timeout or unreachable or failed DNS lookup.
//        }
//    }

    @Scheduled(fixedRate = 3000)
    public void pingPorts() {
        portsList = portsService.getAll();

        for (Ports p : portsList) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(p.getIp(), Integer.parseInt(p.getPort())));
                System.out.println("Success" + p.getSubservice());
            } catch (IOException e) {
                System.out.println(p.getSubservice() + " Does not work");
            }
        }
    }
}
