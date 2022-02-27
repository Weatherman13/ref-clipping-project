package ru.thirteenth.ref_clipping_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data

@MappedSuperclass
public abstract class Ref implements Serializable {

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name="id")
    private Long id;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name="creation_time")

    LocalDateTime created;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name="updating_time")

    LocalDateTime updated;
}
