package ru.thirteenth.ref_clipping_service.service;

import ru.thirteenth.ref_clipping_service.entity.DefaultRef;

public interface DefaultRefService {

    DefaultRef getById(Integer id);

    DefaultRef getDefaultRefByToken(String token);


    Boolean existsByClientToken(String token);

    void save(DefaultRef ref);

    Boolean existsByUrl(String url);

    DefaultRef getDefaultRefByClipRef_Url(String url);

}
