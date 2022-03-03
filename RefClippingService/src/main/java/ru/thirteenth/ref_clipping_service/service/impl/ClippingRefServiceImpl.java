package ru.thirteenth.ref_clipping_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.thirteenth.ref_clipping_service.dao.ClippingRefRepository;
import ru.thirteenth.ref_clipping_service.entity.ClippingRef;
import ru.thirteenth.ref_clipping_service.entity.DefaultRef;
import ru.thirteenth.ref_clipping_service.exception.dao.ClippingRefNotFoundException;


import java.util.Optional;

@Service
public class ClippingRefServiceImpl {
    ClippingRefRepository repository;

    @Autowired
    public ClippingRefServiceImpl(ClippingRefRepository repository) {
        this.repository = repository;
    }


    public void save(ClippingRef ref) {
        repository.save(ref);
    }

    public Optional<ClippingRef> getByDefaultRef(DefaultRef ref){
        return repository.getByDefaultRef(ref);
    }

    public ClippingRef getById(int id){
        return repository.getById(id);
    }





}
