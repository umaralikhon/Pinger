package uz.ziraatbank.pinger.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ziraatbank.pinger.model.entity.Ports;

import javax.sound.sampled.Port;
import java.time.LocalDateTime;
import java.util.List;

public interface PortsRepository extends JpaRepository<Ports, Long> {
    Ports findByHostAndPort(String host, Integer port);
    List<Ports> findAllByServiceName(String serviceName);
    Ports findByServiceName(String serviceName);
}
