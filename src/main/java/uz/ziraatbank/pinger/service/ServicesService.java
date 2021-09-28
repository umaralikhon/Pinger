package uz.ziraatbank.pinger.service;

import uz.ziraatbank.pinger.entity.Services;
import uz.ziraatbank.pinger.repository.ServicesRepository;

import java.util.List;

public interface ServicesService {
    List<Services> findAll();
}
