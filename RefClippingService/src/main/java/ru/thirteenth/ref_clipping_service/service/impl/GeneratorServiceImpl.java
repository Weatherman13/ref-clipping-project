package ru.thirteenth.ref_clipping_service.service.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.thirteenth.ref_clipping_service.service.GeneratorService;



@Service
public class GeneratorServiceImpl implements GeneratorService {

    public String generate() { /*<---------Just generates a random STRING of a given value*/
        var result = RandomStringUtils.random(8,true,true);
        return result;
    }
}
