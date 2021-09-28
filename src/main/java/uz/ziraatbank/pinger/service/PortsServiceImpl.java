package uz.ziraatbank.pinger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ziraatbank.pinger.entity.Ports;
import uz.ziraatbank.pinger.repository.PortsRepository;

import java.util.List;

@Service
public class PortsServiceImpl implements PortsService{
    private PortsRepository portsRepository;

    @Autowired
    public PortsServiceImpl(PortsRepository portsRepository){
        this.portsRepository = portsRepository;
    }

    @Override
    public List<Ports> getAll(){
        return portsRepository.findAll();
    }
}
