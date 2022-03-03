package ru.thirteenth.ref_clipping_service.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@Table(name = "default_ref")
public class DefaultRef  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="client_token")
    private String clientToken;

    @Column(name="url")
    private String url;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name="creation_time")

    private LocalDateTime created;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name="updating_time")

    private LocalDateTime updated;



    @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="clipping_ref_id")
    private ClippingRef clippingRef;

    public DefaultRef( String url, String clientToken) {
        this.clientToken = clientToken;
        this.created = LocalDateTime.now();
        this.url = url;
    }



}




