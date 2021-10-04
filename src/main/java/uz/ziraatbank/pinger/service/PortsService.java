package uz.ziraatbank.pinger.service;

import uz.ziraatbank.pinger.entity.Ports;

import java.util.List;

public interface PortsService {
    List<Ports> getAll();
    void save(Ports port);
}
