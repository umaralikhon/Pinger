package uz.ziraatbank.pinger.model.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.ziraatbank.pinger.model.entity.PingTime;
import uz.ziraatbank.pinger.model.entity.Ports;
import uz.ziraatbank.pinger.model.repository.PingTimeRepository;

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


    public List<String> getTimes(String serviceName) {
        Ports port = portsService.findByServiceName(serviceName);
        List<PingTime> timeList = pingTimeRepo.findAllByTimeBetween(LocalDateTime.now().minusMinutes(90), LocalDateTime.now());
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
        List<PingTime> timeoutList = pingTimeRepo.findAllByTimeBetween(LocalDateTime.now().minusMinutes(90), LocalDateTime.now());
        List<String> timeouts = new ArrayList<>();


        for (PingTime p : timeoutList) {
            if (p.getPorts().getServiceName().equals(port.getServiceName())) {
                timeouts.add(p.getTimeout().toString());
            }
        }

        return timeouts;
    }

    @Scheduled(fixedRateString = "PT80H")
    public void deleteOldHistory(){
        pingTimeRepo.deleteAllByTimeBefore(LocalDateTime.now().minusHours(80));
    }

}
