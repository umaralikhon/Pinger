package example.pinger.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import example.pinger.model.entity.Ports;

import java.util.List;

public interface PortsRepository extends JpaRepository<Ports, Long> {
    Ports findByHostAndPort(String host, Integer port);
    List<Ports> findAllByServiceName(String serviceName);
    Ports findByServiceName(String serviceName);
}
