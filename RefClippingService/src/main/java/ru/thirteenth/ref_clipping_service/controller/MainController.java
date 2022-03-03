package ru.thirteenth.ref_clipping_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.thirteenth.ref_clipping_service.entity.ClippingRef;
import ru.thirteenth.ref_clipping_service.entity.DefaultRef;
import ru.thirteenth.ref_clipping_service.entity.dao.DefaultUri;
import ru.thirteenth.ref_clipping_service.service.ClientService;
import ru.thirteenth.ref_clipping_service.service.impl.ClippingRefServiceImpl;
import ru.thirteenth.ref_clipping_service.service.impl.DefaultRefServiceImpl;
import ru.thirteenth.ref_clipping_service.service.impl.GeneratorServiceImpl;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/service")
public class MainController {

    private DefaultRefServiceImpl defRepository;
    private ClippingRefServiceImpl clipRepository;
    private GeneratorServiceImpl generatorService;
    private ClientService clientService;

    @Autowired
    public MainController(DefaultRefServiceImpl defRepository,
                          ClippingRefServiceImpl clipRepository,
                          GeneratorServiceImpl generatorService,
                          ClientService clientService) {
        this.clientService = clientService;
        this.defRepository = defRepository;
        this.clipRepository = clipRepository;
        this.generatorService = generatorService;
    }


    @PostMapping(value = "/get-clip", consumes = "application/json", produces = "application/json")
    public String getClipRef(@RequestBody DefaultUri url) throws InterruptedException {

        return clientService.getClippingRef(url);
    }


    @PostMapping(value = "/get-def-by-clip", consumes = "application/json", produces = "application/json")
    public String getDefRefByClipRef(String defUrl) {
        return clientService.getDefByClip(defUrl);
    }



}
