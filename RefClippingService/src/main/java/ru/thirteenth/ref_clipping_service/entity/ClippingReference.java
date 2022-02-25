package ru.thirteenth.ref_clipping_service.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ClippingReference extends Ref {
    private String url;
}
