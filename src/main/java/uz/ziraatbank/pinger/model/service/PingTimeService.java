package uz.ziraatbank.pinger.model.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ziraatbank.pinger.model.entity.PingTime;
import uz.ziraatbank.pinger.model.repository.PingTimeRepository;

@Service
@AllArgsConstructor
public class PingTimeService {

    private final PingTimeRepository pingTimeRepo;

    public PingTime save(PingTime pingTime){
        return pingTimeRepo.save(pingTime);
    }
}
