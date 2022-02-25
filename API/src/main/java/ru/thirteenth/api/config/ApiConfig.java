package ru.thirteenth.api.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Configuration
public class ApiConfig {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("Topic2")
                .replicas(1)
                .partitions(3)
                .compact()
                .build();
    }
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
