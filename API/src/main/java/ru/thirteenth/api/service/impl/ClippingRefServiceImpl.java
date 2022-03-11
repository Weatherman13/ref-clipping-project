package ru.thirteenth.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.thirteenth.api.dao.ClippingRefRepository;
import ru.thirteenth.api.entity.ClippingRef;
import ru.thirteenth.api.exception.dto.RefNotFoundException;
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

    public ClippingRef getByDefaultRef_Url(String url){
        return repository.findByDefaultRef_Url(url)
                .orElseThrow(()-> new RefNotFoundException("Short link not found"));
    }

    public ClippingRef getByUrl(String url){
        return repository.findByUrl(url)
                .orElseThrow(()-> new RefNotFoundException("Short link not found"));
    }





}
