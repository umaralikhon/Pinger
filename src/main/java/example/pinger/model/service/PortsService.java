package example.pinger.model.service;

import example.pinger.model.repository.PortsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import example.pinger.model.entity.Ports;

import java.util.List;
import java.util.Optional;

@Service
public class PortsService {

    private final PortsRepository portsRepository;

    @Autowired
    public PortsService(PortsRepository portsRepository){
        this.portsRepository = portsRepository;
    }

    public Ports save(Ports ports){
        return portsRepository.saveAndFlush(ports);
    }

    public Ports findByServiceName(String serviceName){
        return portsRepository.findByServiceName(serviceName);
    }

    public void deleteById(Long id){
        portsRepository.deleteById(id);
    }

    public Optional<Ports> findById(Long id){
        return portsRepository.findById(id);
    }

    public List<Ports> findAll(){
        return portsRepository.findAll();
    }
    public List<Ports> findAll(String sortBy) {
        return portsRepository.findAll(Sort.by(sortBy));
    }
}
