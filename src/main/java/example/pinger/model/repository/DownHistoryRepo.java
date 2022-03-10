package example.pinger.model.repository;

import example.pinger.model.entity.DownHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DownHistoryRepo extends JpaRepository<DownHistory, Long> {

    List<DownHistory> findAllByServiceName(String serviceName);
    List<DownHistory> findByServiceName(String serviceName);
    void deleteAllByServiceName(String serviceName);
}
