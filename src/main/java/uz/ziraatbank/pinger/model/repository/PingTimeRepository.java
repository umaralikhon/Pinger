package uz.ziraatbank.pinger.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ziraatbank.pinger.model.entity.PingTime;

import java.time.LocalDateTime;
import java.util.List;

public interface PingTimeRepository extends JpaRepository<PingTime, Long> {
    List<PingTime> findAllByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

}
