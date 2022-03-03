package ru.thirteenth.ref_clipping_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.thirteenth.ref_clipping_service.service.ClientService;

@RestController
public class RedirectController {
    ClientService clientService;

    @Autowired
    public RedirectController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/{url}")
    public String redirect(@PathVariable String url){
        return clientService.getDefByClip(url);
    }
}
