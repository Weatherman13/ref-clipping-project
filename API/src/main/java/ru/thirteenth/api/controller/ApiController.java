package ru.thirteenth.api.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.thirteenth.api.entity.dao.DefaultUrl;
import ru.thirteenth.api.entity.dao.ExceptionDetails;
import ru.thirteenth.api.entity.dao.StringResponse;
import ru.thirteenth.api.service.impl.ClientService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@RestController
@RequestMapping("/api")
public class ApiController {

    public static final URI GET_NEW_CLIP_REF = URI.create("http://ref-clipping-service/service/get-clip");
    public static final URI GET_DEF_REF_BY_CLIP_REF = URI.create("http://ref-clipping-service/service/get-def-by-clip");
    public static final URI REDIRECT = URI.create("http://ref-clipping-service/service/get-def-by-clip");


    private final RestTemplate restTemplate;
    private final ClientService refService;

    @Autowired
    public ApiController(RestTemplate restTemplate,
                         ClientService refService) {

        this.restTemplate = restTemplate;
        this.refService = refService;
    }


    @PostMapping(value = "/service/create",
                 consumes = "application/json",
                 produces = "application/json")
    @SneakyThrows
    public StringResponse createNewClipRef(@RequestBody DefaultUrl url) {
        refService.defValidation(url);
        refService.send(url);
        var result = restTemplate.postForObject(GET_NEW_CLIP_REF, url, String.class);
        return new StringResponse(result);

    }

    @PostMapping(value = "/get-clip-ref")
    public StringResponse getClipRefByDefRef(@RequestBody DefaultUrl url){
        refService.clipValidation(url);
        var result = restTemplate.postForObject(GET_DEF_REF_BY_CLIP_REF, url, String.class);
        return new StringResponse(result);
    }

    @SneakyThrows
    @GetMapping(value = "/go-to/{clip-ref}")
    public void redirect(@PathVariable("clip-ref") String clipRef, HttpServletResponse response) {
        DefaultUrl url = new DefaultUrl();
        url.setUri(clipRef);
        var result = restTemplate.postForObject(GET_DEF_REF_BY_CLIP_REF, url, String.class);
        response.sendRedirect(result);
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
