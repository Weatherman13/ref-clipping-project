package ru.thirteenth.api.service.impl;


import lombok.SneakyThrows;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.thirteenth.api.entity.dao.DefaultUrl;
import ru.thirteenth.api.exception.dao.BlankException;
import ru.thirteenth.api.exception.dao.ClippingRefNotFoundException;
import ru.thirteenth.api.exception.dao.ExpiredLinkException;
import ru.thirteenth.api.service.KafkaProducer;


import java.net.URL;
import java.util.UUID;
@Slf4j
@Service
public class ValidationService implements KafkaProducer {

    private final KafkaTemplate<Long, DefaultUrl> kafkaTemplate;
    @Value("${kafka.key}")
    private Long kafkaKey;

    public ClippingRefServiceImpl clipRepository;

    private ExpirationDateCheckerServiceCheckerServiceImpl expirationDateChecker;


    @Autowired
    public ValidationService(KafkaTemplate<Long, DefaultUrl> kafkaTemplate,
                             ClippingRefServiceImpl clipRepository,
                             ExpirationDateCheckerServiceCheckerServiceImpl expirationDateChecker) {
        this.kafkaTemplate = kafkaTemplate;
        this.clipRepository = clipRepository;
        this.expirationDateChecker = expirationDateChecker;
    }

    @Override
    public void send(DefaultUrl uri) {
        kafkaTemplate.send("Topic2", kafkaKey, uri);
    }


    @SneakyThrows
    public void defValidation(DefaultUrl uri) {                               /*<------Validates a standard link*/

        if (uri.getUrl().isBlank() || uri.getUrl().isEmpty())
            throw new BlankException("The 'url' field must not be empty or null");
        URL url = new URL(uri.getUrl());                                        /*Checking if our URL string is*/

        uri.setClientToken(UUID.randomUUID());   /*We generate a unique client token by which our specific URL can be found*/
    }

    @SneakyThrows
    public void clipValidation(String url) {                               /*<-------Validates a short link*/

        if (url.isBlank() || url.isEmpty()) {
            throw new BlankException("The 'url' field must not be empty or null");
        }

        if (!clipRepository.existsClippingRefByUrl(url))
            throw new ClippingRefNotFoundException("Such a short link was not found");

        if (expirationDateChecker.checkingExpirationDate(url)) {
            throw new ExpiredLinkException("Short link expired");
        }

    }


}
