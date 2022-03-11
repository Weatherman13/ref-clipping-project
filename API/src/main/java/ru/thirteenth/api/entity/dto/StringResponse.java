package ru.thirteenth.api.entity.dto;

import lombok.Data;

/*This wrapper class is designed to return String in json*/
@Data
public class StringResponse {
    private String response;

    public StringResponse(String response) {
        this.response = response;
    }
}
