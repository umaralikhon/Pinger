package uz.ziraatbank.pinger.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ziraatbank.pinger.entity.Services;
import uz.ziraatbank.pinger.repository.ServicesRepository;
import uz.ziraatbank.pinger.service.ServicesService;
import java.util.List;

@Service
public class ServicesServiceImpl implements ServicesService {
    private ServicesRepository servicesRepository;

    @Autowired
    public ServicesServiceImpl(ServicesRepository servicesRepository){
        this.servicesRepository = servicesRepository;
    }

    public List<Services> getAll(){
        return servicesRepository.findAll();
    }
}
