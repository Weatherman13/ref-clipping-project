package ru.thirteenth.ref_clipping_service.service;

import ru.thirteenth.ref_clipping_service.entity.ClippingRef;




public interface ClippingRefService {

    ClippingRef getByDefaultRef_Url(String url);

    ClippingRef getByUrl(String url);


}
