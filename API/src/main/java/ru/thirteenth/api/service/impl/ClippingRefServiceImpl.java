package ru.thirteenth.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.thirteenth.api.dao.ClippingRefRepository;
import ru.thirteenth.api.service.ClippingRefService;


@Service
public class ClippingRefServiceImpl implements ClippingRefService {
    ClippingRefRepository repository;

    @Autowired
    public ClippingRefServiceImpl(ClippingRefRepository repository) {
        this.repository = repository;
    }


    @Override
    public Boolean existsClippingRefByUrl(String url){
        return repository.existsClippingRefByUrl(url);
    }





}
