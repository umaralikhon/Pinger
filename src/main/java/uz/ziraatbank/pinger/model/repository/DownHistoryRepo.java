package uz.ziraatbank.pinger.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ziraatbank.pinger.model.entity.DownHistory;

import java.util.List;

public interface DownHistoryRepo extends JpaRepository<DownHistory, Long> {

    List<DownHistory> findAllByServiceName(String serviceName);
    List<DownHistory> findByServiceName(String serviceName);
    void deleteAllByServiceName(String serviceName);
}
