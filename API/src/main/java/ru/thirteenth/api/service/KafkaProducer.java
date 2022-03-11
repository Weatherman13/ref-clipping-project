package ru.thirteenth.api.service;

import ru.thirteenth.api.entity.dto.DefaultUrl;

public interface KafkaProducer {
    void send(DefaultUrl uri);


}
