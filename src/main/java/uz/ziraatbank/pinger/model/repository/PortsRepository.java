package uz.ziraatbank.pinger.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ziraatbank.pinger.model.entity.Ports;

public interface PortsRepository extends JpaRepository<Ports, Long> {
}
