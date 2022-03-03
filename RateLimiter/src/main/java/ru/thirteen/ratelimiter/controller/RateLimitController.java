package ru.thirteen.ratelimiter.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
@Slf4j
@RestController
@RequestMapping("/rate-limit")
public class RateLimitController {
    private final Bucket bucket;
    private boolean toggle;

    public RateLimitController() {
        Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofSeconds(30)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping(value = "/getToken")
    public Boolean getToken() {
        log.info(bucket.getAvailableTokens() + " :tokens are available");
        toggle = false;
        if (bucket.tryConsume(1)) toggle = true;
        else toggle = false;
        return toggle;
    }
}
