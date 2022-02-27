package ru.thirteenth.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor

@Entity
@Table(name = "clipping_ref")
public class ClippingRef extends Ref {

    @Column(name="url")
    private String url;


    @OneToOne(mappedBy = "clippingRef", cascade = CascadeType.ALL)
    private DefaultRef defaultRef;
}