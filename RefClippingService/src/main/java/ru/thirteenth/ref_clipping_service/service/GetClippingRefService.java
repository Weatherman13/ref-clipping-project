package ru.thirteenth.ref_clipping_service.service;

import ru.thirteenth.ref_clipping_service.entity.dao.DefaultUrl;

public interface GetClippingRefService {

    String getClippingRef(DefaultUrl uri);
}
