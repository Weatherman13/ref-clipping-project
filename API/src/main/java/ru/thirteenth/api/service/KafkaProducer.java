package ru.thirteenth.api.service;

import ru.thirteenth.api.entity.dao.DefaultUrl;

public interface KafkaProducer {
    void send(DefaultUrl uri);


}
