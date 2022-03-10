package example.pinger.controller;

import example.pinger.model.entity.DownHistory;
import example.pinger.model.entity.Ports;
import example.pinger.model.service.DownHistoryService;
import example.pinger.model.service.PingTimeService;
import example.pinger.model.service.PortsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PingerController {

    private final PingTimeService pingTimeService;
    private final PortsService portsService;
    private final DownHistoryService historyService;

    @GetMapping(path = "/ping")
    public String pingerView(Model model) {

        model.addAttribute("ports", portsService.findAll("id"));
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
        portsService.save(ports);
        return "redirect:/ping";
    }

    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        Optional<Ports> service = portsService.findById(id);
        String serviceName = service.get().getServiceName();
        historyService.deleteAllByServiceName(serviceName);
        portsService.deleteById(id);
        return "redirect:/ping";
    }

    @RequestMapping("/charts/{serviceName}")
    public String showGraphics(@PathVariable String serviceName, Model model) {
        Map<String, List<String>> modelMap = new HashMap<>();
        modelMap.put("times", pingTimeService.getTimes(serviceName));
        modelMap.put("timeouts", pingTimeService.getTimeouts(serviceName));
        modelMap.put("serviceName", Collections.singletonList(serviceName));
        model.addAllAttributes(modelMap);

        System.out.println(pingTimeService.getTimes(serviceName));
        System.out.println(pingTimeService.getTimeouts(serviceName));

        return "chartsView";
    }

    @RequestMapping("/history/{serviceName}")
    public String history(@PathVariable String serviceName, Model model) {

        List<DownHistory> histories = historyService.findAllByServiceName(serviceName);

        if (histories.size() == 0) {
            return "emptyHistory";
        } else {
            model.addAttribute("history", histories);
            return "historyView";
        }
    }

    @RequestMapping("/history/delete/{serviceName}")
    public String deleteHistory(@PathVariable String serviceName) {
        historyService.deleteAllByServiceName(serviceName);
        return "redirect:/ping";
    }
}
