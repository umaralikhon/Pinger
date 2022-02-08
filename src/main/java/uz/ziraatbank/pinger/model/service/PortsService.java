package uz.ziraatbank.pinger.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ziraatbank.pinger.model.entity.Ports;
import uz.ziraatbank.pinger.model.repository.PortsRepository;

import java.util.List;

@Service
public class PortsService {

    private final PortsRepository portsRepository;

    @Autowired
    public PortsService(PortsRepository portsRepository){
        this.portsRepository = portsRepository;
    }

    public List<Ports> getAll(){
        return portsRepository.findAll();
    }

    public Ports save(Ports ports){
        return portsRepository.saveAndFlush(ports);
    }
}
