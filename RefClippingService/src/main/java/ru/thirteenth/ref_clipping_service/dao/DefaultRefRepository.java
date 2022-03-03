package ru.thirteenth.ref_clipping_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.thirteenth.ref_clipping_service.entity.ClippingRef;
import ru.thirteenth.ref_clipping_service.entity.DefaultRef;

import java.util.Optional;
import java.util.UUID;

public interface DefaultRefRepository extends JpaRepository<DefaultRef, Integer> {


    Optional<DefaultRef> getDefaultRefByClippingRef(ClippingRef ref);

    Optional<DefaultRef> findByUrl(String url);

    Optional<DefaultRef> getDefaultRefByClientToken(String token);

    Boolean existsByUrl(String url);

    Boolean existsByClientToken(String token);

    DefaultRef getById(Integer id);

    Optional<DefaultRef> findDefaultRefByClippingRef_Url(String url);




}
