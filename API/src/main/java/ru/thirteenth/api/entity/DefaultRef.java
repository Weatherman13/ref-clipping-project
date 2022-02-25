package ru.thirteenth.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.UUID;
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DefaultRef extends Ref {
    private String url;


}
