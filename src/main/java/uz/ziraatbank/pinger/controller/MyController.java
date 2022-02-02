package uz.ziraatbank.pinger.controller;

import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.ziraatbank.pinger.model.entity.Ports;
import uz.ziraatbank.pinger.model.repository.PortsRepository;

import javax.sound.sampled.Port;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MyController {

    private final PortsRepository portsRepo;

    @GetMapping(path = "/fuck")
    public String pingerView(Model model) {

        model.addAttribute("ports", portsRepo.findAll());
        return "ping";
    }

    @RequestMapping("/add-service")
    public String addNewService(Model model) {
        Ports ports = new Ports();
        model.addAttribute("service", ports);
        return "addService";
    }

    @PostMapping("/save-service")
    public String saveService(@ModelAttribute("service") Ports ports) {
        portsRepo.save(ports);
        return "redirect:/fuck";
    }

    @RequestMapping("/delete-service")
    public String deleteService(Model model) {
        Ports ports = new Ports();
        model.addAttribute("deletingService", ports);
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("deletingService") Ports ports) {
        Ports port = portsRepo.findByHostAndPort(ports.getHost(), ports.getPort());
        portsRepo.deleteById(port.getId());
        return "redirect:/fuck";
    }
}
