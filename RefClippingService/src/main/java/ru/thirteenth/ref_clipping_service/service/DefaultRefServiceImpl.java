package ru.thirteenth.ref_clipping_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.thirteenth.ref_clipping_service.dao.DefaultRefRepository;
import ru.thirteenth.ref_clipping_service.entity.DefaultRef;

import java.util.List;


@EnableKafka
@Service
public class DefaultRefServiceImpl implements DefaultRefService{

    private DefaultRefRepository repository;

    private final KafkaTemplate<Long, DefaultRef> kafkaTemplate;

    public static final String GROUP_ID = "refClippingService";



    @Autowired public DefaultRefServiceImpl(KafkaTemplate<Long, DefaultRef> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    @Override
    public List<DefaultRef> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(DefaultRef ref) {
        repository.save(ref);
    }

    @Override
    public DefaultRef getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void update(int id, DefaultRef newRef) {
        DefaultRef ref = repository.getById(id);
        ref.setUrl(newRef.getUrl());
        ref.setCreated(newRef.getCreated());
        ref.setUpdated(newRef.getUpdated());
        repository.save(ref);
    }

    @KafkaListener(groupId = GROUP_ID,topics="Topic2", containerFactory = "singleFactory")
    public void consume(DefaultRef dRef){
        System.out.println(dRef.toString());
    }


}
