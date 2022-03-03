package ru.thirteenth.api.service.impl;


import com.sun.xml.bind.v2.TODO;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.thirteenth.api.entity.dao.DefaultUri;
import ru.thirteenth.api.exception.dao.BlankException;
import ru.thirteenth.api.service.DefaultUriService;


import java.net.URL;
import java.util.UUID;

@Service
public class DefaultUriServiceImpl implements DefaultUriService {

    private final KafkaTemplate<Long,DefaultUri> kafkaTemplate;
    @Value("${kafka.key}")
    private Long kafkaKey;


    @Autowired
    public DefaultUriServiceImpl(KafkaTemplate<Long, DefaultUri> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void send(DefaultUri uri) {
        kafkaTemplate.send("Topic2",kafkaKey, uri);
    }


    @SneakyThrows
    public DefaultUri validation(DefaultUri uri) {

        if (uri.getUri().isBlank() || uri.getUri().isEmpty())
            throw new BlankException("The 'url' field must not be empty or null");
        URL url = new URL(uri.getUri());         // TODO: 3/3/2022   Checking if our URL string is



        uri.setClientToken(UUID.randomUUID());  // TODO: 3/3/2022    We generate a unique client token by which
                                                // TODO: 3/3/2022    our specific URL can be found

        return uri;
    }
}
