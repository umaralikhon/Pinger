package uz.ziraatbank.pinger.service;

import org.springframework.stereotype.Service;
import uz.ziraatbank.pinger.entity.Ports;
import uz.ziraatbank.pinger.repository.PortsRepository;

import java.util.List;

public interface PortsService{
    List<Ports> getAll();
}
