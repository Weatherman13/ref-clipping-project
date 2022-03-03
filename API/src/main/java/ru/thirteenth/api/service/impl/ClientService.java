package ru.thirteenth.api.service.impl;


import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.thirteenth.api.entity.dao.DefaultUrl;
import ru.thirteenth.api.exception.dao.BlankException;
import ru.thirteenth.api.exception.dao.DefaultRefNotFoundException;
import ru.thirteenth.api.service.KafkaProducer;


import java.net.URL;
import java.util.UUID;

@Service
public class ClientService implements KafkaProducer {

    private final KafkaTemplate<Long, DefaultUrl> kafkaTemplate;
    @Value("${kafka.key}")
    private Long kafkaKey;

    public ClippingRefServiceImpl clipRepository;


    @Autowired
    public ClientService(KafkaTemplate<Long, DefaultUrl> kafkaTemplate, ClippingRefServiceImpl clipRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.clipRepository = clipRepository;
    }

    @Override
    public void send(DefaultUrl uri) {
        kafkaTemplate.send("Topic2",kafkaKey, uri);
    }


    @SneakyThrows
    public void defValidation(DefaultUrl uri) {                               /*<------Validates a standard link*/

        if (uri.getUri().isBlank() || uri.getUri().isEmpty())
            throw new BlankException("The 'url' field must not be empty or null");
        URL url = new URL(uri.getUri());                                        /*Checking if our URL string is*/


        uri.setClientToken(UUID.randomUUID());   /*We generate a unique client token by which our specific URL can be found*/
    }

    @SneakyThrows
    public void clipValidation(DefaultUrl url){                               /*<-------Validates a short link*/

        if (url.getUri().isBlank() || url.getUri().isEmpty())
            throw new BlankException("The 'url' field must not be empty or null");

        if (!clipRepository.existsClippingRefByUrl(url.getUri()))
            throw new DefaultRefNotFoundException("The default link corresponding to the clipping one was not found");

    }







}
