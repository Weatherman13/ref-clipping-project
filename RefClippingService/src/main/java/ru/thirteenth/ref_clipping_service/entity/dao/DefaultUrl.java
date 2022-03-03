package ru.thirteenth.ref_clipping_service.entity.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultUrl {


    private String uri;
    private UUID clientToken;
}
