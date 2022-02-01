package uz.ziraatbank.pinger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.ziraatbank.pinger.model.repository.PortsRepository;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MyController {

    private final PortsRepository portsRepo;

    @GetMapping(path = "/fuck")
    public String pingerView(Model model){

        model.addAttribute("ports", portsRepo.findAll());
        return "ping";
    }
}
