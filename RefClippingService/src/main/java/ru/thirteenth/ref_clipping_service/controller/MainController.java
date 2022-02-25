package ru.thirteenth.ref_clipping_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.thirteenth.ref_clipping_service.entity.DefaultRef;

import java.util.Random;

@RestController
@Slf4j
@RequestMapping("/ref-clipping")
public class MainController {


    @PostMapping(value ="/test1", consumes = "application/json", produces = "application/json")
    public DefaultRef test(@RequestBody DefaultRef defaultRef){
        log.debug("test1 is completed");


        return defaultRef;
    }


    @GetMapping("/test2")
     public String test2(){
        log.debug("test2 is completed");
        return "test2";
    }

}
