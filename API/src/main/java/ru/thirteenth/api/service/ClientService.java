package ru.thirteenth.api.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.thirteenth.api.entity.dto.DefaultUrl;
import ru.thirteenth.api.entity.dto.StringResponse;
import ru.thirteenth.api.service.impl.ValidationService;

import java.net.URI;
@Slf4j
@Service
public class ClientService {
    public static final URI GET_NEW_CLIP_REF = URI.create("http://ref-clipping-service/service/get-clip");
    public static final URI GET_DEF_REF_BY_CLIP_REF = URI.create("http://ref-clipping-service/service/get-def-by-clip");


    private final ValidationService refService;
    private final RestTemplate restTemplate;

    @Autowired
    public ClientService(ValidationService refService, RestTemplate restTemplate) {
        this.refService = refService;
        this.restTemplate = restTemplate;
    }

    @SneakyThrows
    public StringResponse createClipRef(DefaultUrl url) {
        refService.defValidation(url);
        log.info( url.getUrl() + " is valid");
        refService.send(url);
        var result = restTemplate.postForObject(GET_NEW_CLIP_REF, url, String.class);
        log.info("Short link created");
        return new StringResponse(result);
    }

    public StringResponse getClipRefByDefRef(DefaultUrl url){
        refService.clipValidation(url.getUrl());
        log.info(url.getUrl() + " is valid");
        var result = restTemplate.postForObject(GET_DEF_REF_BY_CLIP_REF, url, String.class);
        log.info("Long link received");
        return new StringResponse(result);
    }

    public String redirect(String ref) {
        var clipRefUrl = "http://localhost:8080/api/go-to/" + ref;
        refService.clipValidation(clipRefUrl);
        log.info(ref + " is valid");
        DefaultUrl url = new DefaultUrl();
        url.setUrl(clipRefUrl);
        var result = restTemplate.postForObject(GET_DEF_REF_BY_CLIP_REF, url, String.class);
        log.info("Long link received: " + result);
        return result;
    }
}
