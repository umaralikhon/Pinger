package uz.ziraatbank.pinger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ziraatbank.pinger.entity.Ports;

public interface PortsRepository extends JpaRepository<Ports, Long> {
}
