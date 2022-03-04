package ru.thirteenth.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;



@Data
@Entity
@NoArgsConstructor
@Table(name = "clipping_ref")
public class ClippingRef  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name="creation_time")
    private LocalDateTime created;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name="updating_time")
    private LocalDateTime updated;

    @Column(name="url")
    private String url;


    @JsonIgnore
    @OneToOne(mappedBy = "clippingRef", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private DefaultRef defaultRef;

    public ClippingRef( String url) {
        this.created = LocalDateTime.now();
        this.url = url;
    }




}
