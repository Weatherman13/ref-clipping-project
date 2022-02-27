package ru.thirteenth.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.thirteenth.api.entity.DefaultRef;

@Service
public class DefaultRefServiceImpl implements DefaultRefService{

    private final KafkaTemplate<Long,DefaultRef> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Value("${kafka.key}")
    private Long kafkaKey;

    @Autowired
    public DefaultRefServiceImpl(KafkaTemplate<Long, DefaultRef> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void send(DefaultRef dRef) {
        kafkaTemplate.send("Topic2",kafkaKey, dRef);
    }

}
