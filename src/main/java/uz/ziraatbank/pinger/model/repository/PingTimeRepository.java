package uz.ziraatbank.pinger.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ziraatbank.pinger.model.entity.PingTime;

public interface PingTimeRepository extends JpaRepository<PingTime, Long> {
}
