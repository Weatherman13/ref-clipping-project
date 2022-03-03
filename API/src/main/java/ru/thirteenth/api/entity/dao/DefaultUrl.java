package ru.thirteenth.api.entity.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultUrl {

    private String uri;
    private UUID clientToken;
}
