package uz.ziraatbank.pinger.model.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ziraatbank.pinger.model.entity.PingTime;
import uz.ziraatbank.pinger.model.entity.Ports;
import uz.ziraatbank.pinger.model.repository.PingTimeRepository;
import uz.ziraatbank.pinger.telnet.Ping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PingTimeService {

    private final PingTimeRepository pingTimeRepo;
    private final PortsService portsService;

    public PingTime save(PingTime pingTime) {
        return pingTimeRepo.save(pingTime);
    }

//    Working part
//    public List<String> getTimes() {
//        List<PingTime> pingTimes = pingTimeRepo.findAllByTimeBetween(LocalDateTime.now().minusMinutes(15), LocalDateTime.now());
//        List<String> times = new ArrayList<>();
//
//        for (PingTime p : pingTimes) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//            times.add(p.getTime().format(formatter));
//        }
//
//        return times;
//    }
//
//    public List<String> getTimeouts() {
//        List<PingTime> pingTimes = pingTimeRepo.findAllByTimeBetween(LocalDateTime.now().minusMinutes(15), LocalDateTime.now());
//        List<String> timeouts = new ArrayList<>();
//
//        for (PingTime p : pingTimes) {
//            timeouts.add(p.getTimeout().toString());
//        }
//
//        return timeouts;
//    }

    public List<String> getTimes(String serviceName) {
        Ports port = portsService.findByServiceName(serviceName);
        List<PingTime> timeList = pingTimeRepo.findAllByTimeBetween(LocalDateTime.now().minusMinutes(15), LocalDateTime.now());
        List<String> times = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (PingTime p : timeList) {
            if (p.getPorts().getServiceName().equals(port.getServiceName())) {
                times.add(p.getTime().format(formatter));
            }
        }

        return times;
    }

    public List<String> getTimeouts(String serviceName) {
        Ports port = portsService.findByServiceName(serviceName);
        List<PingTime> timeoutList = pingTimeRepo.findAllByTimeBetween(LocalDateTime.now().minusMinutes(15), LocalDateTime.now());
        List<String> timeouts = new ArrayList<>();


        for (PingTime p : timeoutList) {
            if (p.getPorts().getServiceName().equals(port.getServiceName())) {
                timeouts.add(p.getTimeout().toString());
            }
        }

        return timeouts;
    }

}
