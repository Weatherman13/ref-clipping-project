package ru.thirteenth.ref_clipping_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Slf4j
@RequestMapping("/ref-clipping")
public class MainController {
    Random random = new Random();

    @GetMapping("/test1")
    public String test(){
        log.debug("test1 is completed");
        System.out.println(random.nextInt());
        return "test1";
    }
    @GetMapping("/test2")
     public String test2(){
        log.debug("test2 is completed");
        return "test2";
    }

}
