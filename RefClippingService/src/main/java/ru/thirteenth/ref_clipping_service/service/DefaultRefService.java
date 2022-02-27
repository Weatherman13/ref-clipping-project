package ru.thirteenth.ref_clipping_service.service;

import ru.thirteenth.ref_clipping_service.entity.DefaultRef;

import java.util.List;

public interface DefaultRefService {
    public List<DefaultRef> getAll();

    public void save(DefaultRef ref);

    public DefaultRef getById(int id);

    public void deleteById(int id);

    public void update(int id, DefaultRef newRef);

    public void consume(DefaultRef dRef);
}
