package ru.thirteenth.api.entity.dao;

import lombok.Data;

// TODO: 3/3/2022  This wrapper class is designed to return String in json
@Data
public class StringResponse {
    private String response;

    public StringResponse(String response) {
        this.response = response;
    }
}
