package ru.thirteenth.ref_clipping_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.thirteenth.ref_clipping_service.dao.ClippingRefRepository;
import ru.thirteenth.ref_clipping_service.entity.ClippingRef;
import ru.thirteenth.ref_clipping_service.exception.dao.ClippingRefNotFoundException;
import ru.thirteenth.ref_clipping_service.service.ClippingRefService;


@Service
public class ClippingRefServiceImpl implements ClippingRefService {
    ClippingRefRepository repository;

    @Autowired
    public ClippingRefServiceImpl(ClippingRefRepository repository) {
        this.repository = repository;
    }



    public ClippingRef getByDefaultRef_Url(String url){
        return repository.findByDefaultRef_Url(url)
                .orElseThrow(()-> new ClippingRefNotFoundException("Short link not found"));
    }

    public ClippingRef getByUrl(String url){
        return repository.findByUrl(url)
                .orElseThrow(()-> new ClippingRefNotFoundException("Short link not found"));
    }





}
