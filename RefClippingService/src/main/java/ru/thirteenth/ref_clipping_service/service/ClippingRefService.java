package ru.thirteenth.ref_clipping_service.service;

import ru.thirteenth.ref_clipping_service.entity.ClippingRef;

import java.util.List;

public interface ClippingRefService {

    public List<ClippingRef> getAll();

    public void save(ClippingRef ref);

    public ClippingRef getById(int id);

    public void deleteById(int id);
}
