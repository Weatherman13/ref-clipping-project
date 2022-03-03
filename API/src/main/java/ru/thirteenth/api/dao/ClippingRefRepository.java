package ru.thirteenth.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.thirteenth.api.entity.ClippingRef;

import java.util.Optional;

public interface ClippingRefRepository extends JpaRepository<ClippingRef,Integer> {

    Boolean existsClippingRefByUrl(String url);

    Optional<ClippingRef> findByDefaultRef_Url(String url);

    Optional<ClippingRef> findByUrl(String url);

}
