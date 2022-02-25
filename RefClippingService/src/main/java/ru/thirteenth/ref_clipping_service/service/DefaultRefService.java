package ru.thirteenth.ref_clipping_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.thirteenth.ref_clipping_service.entity.DefaultRef;

import java.util.Random;


@EnableKafka
@Service
public class DefaultRefService {
    private final KafkaTemplate<Long, DefaultRef> kafkaTemplate;
    public static final String GROUP_ID = "refClippingService";




    @Autowired
    public DefaultRefService(KafkaTemplate<Long, DefaultRef> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(groupId = GROUP_ID,topics="Topic2", containerFactory = "singleFactory")
    public void consume(DefaultRef dRef){
        System.out.println(dRef.toString());
    }


}
