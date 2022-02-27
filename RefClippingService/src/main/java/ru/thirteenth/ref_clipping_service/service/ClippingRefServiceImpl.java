package ru.thirteenth.ref_clipping_service.service;

import ru.thirteenth.ref_clipping_service.dao.ClippingRefRepository;
import ru.thirteenth.ref_clipping_service.entity.ClippingRef;

import java.util.List;

public class ClippingRefServiceImpl implements ClippingRefService{
    ClippingRefRepository repository;

    @Override
    public List<ClippingRef> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(ClippingRef ref) {
        repository.save(ref);
    }

    @Override
    public ClippingRef getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);

    }
}
