package uz.ziraatbank.pinger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ziraatbank.pinger.entity.Services;

public interface ServicesRepository extends JpaRepository<Services, Long> {
}
