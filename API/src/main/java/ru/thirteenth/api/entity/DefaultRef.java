package ru.thirteenth.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Random;
import java.util.UUID;
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
