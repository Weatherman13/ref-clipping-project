package ru.thirteenth.ref_clipping_service.service.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.thirteenth.ref_clipping_service.entity.ClippingRef;
import ru.thirteenth.ref_clipping_service.entity.DefaultRef;
import ru.thirteenth.ref_clipping_service.entity.dao.DefaultUrl;
import ru.thirteenth.ref_clipping_service.exception.dao.RequestTimeoutExceededException;
import ru.thirteenth.ref_clipping_service.service.impl.DefaultRefServiceImpl;
import ru.thirteenth.ref_clipping_service.service.impl.GeneratorServiceImpl;


import java.net.URI;

@EnableKafka
@Service
public class ConsumerService {
    public static final URI GET_TO_RATE_LIMITER = URI.create("http://rate-limiter/rate-limit/getToken");
    public static final String GROUP_ID = "refClippingService";
    private final KafkaTemplate<Long, DefaultUrl> kafkaTemplate;
    private final RestTemplate restTemplate;
    private GeneratorServiceImpl generatorService;
    private DefaultRefServiceImpl defRepository;

    @Autowired
    public ConsumerService(KafkaTemplate<Long, DefaultUrl> kafkaTemplate,
                           RestTemplate restTemplate,
                           GeneratorServiceImpl generatorService,
                           DefaultRefServiceImpl defRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
        this.generatorService = generatorService;
        this.defRepository = defRepository;
    }


    @KafkaListener(groupId = GROUP_ID, topics = "Topic2", containerFactory = "singleFactory")
    public void consume(DefaultUrl uri) throws InterruptedException {

        while (true) {
            int counter = 0;
            var result = restTemplate.getForObject(GET_TO_RATE_LIMITER, Boolean.class);
            if (result) {
                saveToDatabase(uri);
                break;
            } else {
                counter++;
                if (counter >= 7)
                    throw new RequestTimeoutExceededException("Request timeout exceeded, try again later");
                Thread.sleep(2000);
            }
        }

    }


    public void saveToDatabase(DefaultUrl uri) {
        DefaultRef defaultRef = new DefaultRef(uri.getUri(), uri.getClientToken().toString());
        ClippingRef clippingRef = new ClippingRef(generatorService.generate());
        defaultRef.setClippingRef(clippingRef);
        clippingRef.setDefaultRef(defaultRef);
        defRepository.save(defaultRef);

    }
}
