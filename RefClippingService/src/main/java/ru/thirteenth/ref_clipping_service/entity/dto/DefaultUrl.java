package ru.thirteenth.ref_clipping_service.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultUrl {


    private String url;
    private UUID clientToken;
}
