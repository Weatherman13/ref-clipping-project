package ru.thirteenth.ref_clipping_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.thirteenth.ref_clipping_service.entity.DefaultRef;

public interface DefaultRefRepository extends JpaRepository<DefaultRef, Integer> {
}
