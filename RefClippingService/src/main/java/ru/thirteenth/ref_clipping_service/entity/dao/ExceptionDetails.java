package ru.thirteenth.ref_clipping_service.entity.dao;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDetails {
    public String timestamp;
    public String message;
    public Integer status;


}
