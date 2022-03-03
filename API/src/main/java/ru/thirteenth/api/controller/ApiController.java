package ru.thirteenth.api.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.thirteenth.api.entity.dao.DefaultUri;
import ru.thirteenth.api.entity.dao.ExceptionDetails;
import ru.thirteenth.api.entity.dao.StringResponse;
import ru.thirteenth.api.service.impl.DefaultUriServiceImpl;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/api")
public class ApiController {

    public static final URI GET_NEW_CLIP_REF = URI.create("http://ref-clipping-service/service/get-clip");
    public static final URI GET_DEF_REF_BY_CLIP_REF = URI.create("http://ref-clipping-service/service//get-def-by-clip");


    private final RestTemplate restTemplate;
    private final DefaultUriServiceImpl refService;

    @Autowired
    public ApiController(RestTemplate restTemplate,
                         DefaultUriServiceImpl refService) {

        this.restTemplate = restTemplate;
        this.refService = refService;
    }


    @PostMapping(value = "/service/create",
                 consumes = "application/json",
                 produces = "application/json")
    @SneakyThrows
    public StringResponse createNewClipRef(@RequestBody DefaultUri url) {
        refService.validation(url);
        refService.send(url);
        var result = restTemplate.postForObject(GET_NEW_CLIP_REF, url, String.class);
        return new StringResponse(result);

    }

    @PostMapping(value = "/get-clip-ref")
    public StringResponse getClipRefByDefRef(@RequestBody DefaultUri url){
        var result = restTemplate.postForObject(GET_DEF_REF_BY_CLIP_REF, url, String.class);
        return new StringResponse(result);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionDetails> handleNullPointerException(
            HttpServletRequest request,
            Exception exception)
    {
        return ResponseEntity.status(BAD_REQUEST).body(new ExceptionDetails(
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                "The value of 'url' cannot be null",
                BAD_REQUEST.value()
        ));
    }


}
