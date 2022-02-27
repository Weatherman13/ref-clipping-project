package ru.thirteenth.ref_clipping_service.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor

@Entity
@Table(name = "default_ref")
public class DefaultRef extends Ref {

    @Column(name="url")
    private String url;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="clipping_ref_id")
    private ClippingRef clippingRef;


}
