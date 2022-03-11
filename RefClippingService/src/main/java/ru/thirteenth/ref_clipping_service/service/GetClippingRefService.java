package ru.thirteenth.ref_clipping_service.service;

import ru.thirteenth.ref_clipping_service.entity.dto.DefaultUrl;

public interface GetClippingRefService {

    String getClippingRef(DefaultUrl uri);
}
