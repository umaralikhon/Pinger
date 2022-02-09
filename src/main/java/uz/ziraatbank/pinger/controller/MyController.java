package uz.ziraatbank.pinger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.ziraatbank.pinger.model.entity.Ports;
import uz.ziraatbank.pinger.model.repository.PortsRepository;
import uz.ziraatbank.pinger.model.service.PingTimeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MyController {

    private final PortsRepository portsRepo;
    private final PingTimeService pingTimeService;

    @GetMapping(path = "/ping")
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
        return "redirect:/ping";
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
        return "redirect:/ping";
    }

    @RequestMapping("/charts/{serviceName}")
    public String showGraphics(@PathVariable String serviceName, Model model){
        Map<String, List<String>> modelMap = new HashMap<>();
        modelMap.put("times", pingTimeService.getTimes(serviceName));
        modelMap.put("timeouts", pingTimeService.getTimeouts(serviceName));
        model.addAllAttributes(modelMap);


        System.out.println(pingTimeService.getTimes(serviceName));
        System.out.println(pingTimeService.getTimeouts(serviceName));

        return "chartsView";
    }


}
