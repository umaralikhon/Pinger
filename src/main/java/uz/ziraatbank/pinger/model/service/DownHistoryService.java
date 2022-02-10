package uz.ziraatbank.pinger.model.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ziraatbank.pinger.config.Status;
import uz.ziraatbank.pinger.model.entity.DownHistory;
import uz.ziraatbank.pinger.model.repository.DownHistoryRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DownHistoryService {

    private final DownHistoryRepo downHistoryRepo;

    public void save(DownHistory downHistory) {
        downHistoryRepo.save(downHistory);
    }

    public void deleteAll() {
        downHistoryRepo.deleteAll();
    }

    public List<DownHistory> findAllByServiceName(String serviceName) {
        return downHistoryRepo.findAllByServiceName(serviceName);
    }

    public void deleteAllByServiceName(String serviceName){
        downHistoryRepo.deleteAllByServiceName(serviceName);
    }

    public void saveItems(String serviceName, String host, Integer port, String time, Status status) {

        DownHistory downHistory = new DownHistory(serviceName, host, port, time, status);
        List<DownHistory> downHistories = downHistoryRepo.findByServiceName(serviceName);

        if (downHistories.size() == 0) {
            downHistoryRepo.save(downHistory);
        }

        downHistoryRepo.save(downHistory);
    }
}
