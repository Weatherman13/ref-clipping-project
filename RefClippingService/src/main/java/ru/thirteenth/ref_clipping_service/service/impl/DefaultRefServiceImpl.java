package ru.thirteenth.ref_clipping_service.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.thirteenth.ref_clipping_service.dao.DefaultRefRepository;
import ru.thirteenth.ref_clipping_service.entity.DefaultRef;
import ru.thirteenth.ref_clipping_service.exception.dao.ClippingRefNotFoundException;
import ru.thirteenth.ref_clipping_service.exception.dao.DefaultRefNotFoundException;
import ru.thirteenth.ref_clipping_service.service.DefaultRefService;


@Service
public class DefaultRefServiceImpl implements DefaultRefService {


    private DefaultRefRepository repository;

    @Autowired
    public DefaultRefServiceImpl(DefaultRefRepository repository) {
        this.repository = repository;
    }


    public DefaultRef getById(Integer id){
        return  repository.getById(id);
    }

    public DefaultRef getDefaultRefByToken(String token){
        return repository.getDefaultRefByClientToken(token)
                         .orElseThrow(()->
                                 new DefaultRefNotFoundException("A standard link with such parameters was not found"));
    }

    public Boolean existsByClientToken(String token){
        return repository.existsByClientToken(token);
    }

    public void save(DefaultRef ref) {
        repository.save(ref);
    }

    public Boolean existsByUrl(String url) {
        return repository.existsByUrl(url);
    }

    public DefaultRef getDefaultRefByClipRef_Url(String url){
        return repository.findDefaultRefByClippingRef_Url(url)
                .orElseThrow(()->new ClippingRefNotFoundException("No such link was found"));
    }



}
