package ru.thirteenth.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.thirteenth.api.service.ExpirationDateCheckerService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


/*Checks the expiration date of a short link*/
@Slf4j
@Service
public class ExpirationDateCheckerServiceCheckerServiceImpl implements ExpirationDateCheckerService {

    ClippingRefServiceImpl clipService;

    @Autowired
    public ExpirationDateCheckerServiceCheckerServiceImpl(ClippingRefServiceImpl clipService) {
        this.clipService = clipService;
    }


    @Override
    public Boolean checkingExpirationDate(String url) {
        log.info(url + " :сhecking the link for relevance has begun... ");
        var created = clipService.getByUrl(url).getCreated();
        var updated = LocalDateTime.now();
        var differenceInMinutes = ChronoUnit.MINUTES.between(created,updated);
        if (differenceInMinutes>10) return true;
        log.info(url + " :The link is relevant!");
        return false;
    }
}
