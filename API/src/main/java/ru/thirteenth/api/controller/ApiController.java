package ru.thirteenth.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.thirteenth.api.entity.DefaultRef;
import ru.thirteenth.api.service.DefaultRefServiceImpl;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiController {

    public static final String SERVICE_ID = "ref-clipping-service";


    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancer;
    private final DefaultRefServiceImpl refService;

    @Autowired
    public ApiController(RestTemplate restTemplate, LoadBalancerClient loadBalancer, DefaultRefServiceImpl refService) {
        this.restTemplate = restTemplate;
        this.loadBalancer = loadBalancer;
        this.refService = refService;
    }

    @PostMapping(value ="/service/create", consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public DefaultRef test(@RequestBody DefaultRef ref) {
        var refServiceAdress = URI.create("http://" + SERVICE_ID).resolve("/ref-clipping/test1");

        refService.send(ref);

        System.out.println("In work");
        var result = restTemplate.postForObject(refServiceAdress,ref,DefaultRef.class);


        return result;


    }
}
