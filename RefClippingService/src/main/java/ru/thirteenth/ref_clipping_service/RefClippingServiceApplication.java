package ru.thirteenth.ref_clipping_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RefClippingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RefClippingServiceApplication.class, args);
    }

}
