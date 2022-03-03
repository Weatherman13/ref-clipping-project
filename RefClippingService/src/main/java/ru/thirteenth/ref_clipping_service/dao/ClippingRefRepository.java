package ru.thirteenth.ref_clipping_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.thirteenth.ref_clipping_service.entity.ClippingRef;
import ru.thirteenth.ref_clipping_service.entity.DefaultRef;

import java.util.Optional;

public interface ClippingRefRepository extends JpaRepository<ClippingRef,Integer> {

    Optional<ClippingRef> findByDefaultRef(DefaultRef ref);

    Optional<ClippingRef> findByDefaultRef_Url(String url);

    Optional<ClippingRef> findByUrl(String url);




}
