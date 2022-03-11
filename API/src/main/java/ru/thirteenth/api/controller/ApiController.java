package ru.thirteenth.api.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.thirteenth.api.entity.dto.DefaultUrl;
import ru.thirteenth.api.entity.dto.ExceptionDetails;
import ru.thirteenth.api.entity.dto.StringResponse;
import ru.thirteenth.api.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    private ClientService clientService;

    @Autowired
    public ApiController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PostMapping(value = "/service/create",
            consumes = "application/json",
            produces = "application/json")
    public StringResponse createNewClipRef(@RequestBody DefaultUrl url) {
        return clientService.createClipRef(url);
    }

    @PostMapping(value = "/get-clip-ref")
    public StringResponse getClipRefByDefRef(@RequestBody DefaultUrl url) {
        return clientService.getClipRefByDefRef(url);
    }

    @GetMapping(value = "/go-to/{clip-ref}")
    public void redirect(@PathVariable("clip-ref") String endpoint, HttpServletResponse response) {
        String redirectUrl = clientService.redirect(endpoint);
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }


}
