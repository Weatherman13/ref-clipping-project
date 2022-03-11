package ru.thirteenth.ref_clipping_service.service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.thirteenth.ref_clipping_service.entity.ClippingRef;
import ru.thirteenth.ref_clipping_service.entity.DefaultRef;
import ru.thirteenth.ref_clipping_service.entity.dto.DefaultUrl;
import ru.thirteenth.ref_clipping_service.exception.dto.RequestTimeoutExceededException;
import ru.thirteenth.ref_clipping_service.service.impl.DefaultRefServiceImpl;
import ru.thirteenth.ref_clipping_service.service.impl.GeneratorServiceImpl;


import java.net.URI;

@Slf4j
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
    public void consume(DefaultUrl uri) {

        while (true) {
            int counter = 0;
            log.debug(counter + " :attempt to form a short link");
            var result = restTemplate.getForObject(GET_TO_RATE_LIMITER, Boolean.class);
            if (result) {
                saveToDatabase(uri);
                log.debug("The link is formed!");
                break;
            } else {
                counter++;
                if (counter >= 7)
                    throw new RequestTimeoutExceededException("Request timeout exceeded, try again later");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.warn(e.getMessage());
                }
            }
        }

    }


    public void saveToDatabase(DefaultUrl uri) {
        var clipRefUrl = "http://localhost:8080/api/go-to/" + generatorService.generate(8);
        DefaultRef defaultRef = new DefaultRef(uri.getUrl(), uri.getClientToken().toString());
        ClippingRef clippingRef = new ClippingRef(clipRefUrl);
        defaultRef.setClippingRef(clippingRef);
        clippingRef.setDefaultRef(defaultRef);
        defRepository.save(defaultRef);

    }
}
