package ru.thirteenth.api.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.thirteenth.api.entity.dao.DefaultUrl;
import ru.thirteenth.api.entity.dao.ExceptionDetails;
import ru.thirteenth.api.entity.dao.StringResponse;
import ru.thirteenth.api.service.ClientService;
import ru.thirteenth.api.service.impl.ValidationService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
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

    @SneakyThrows
    @PostMapping(value = "/service/create",
                 consumes = "application/json",
                 produces = "application/json")
    public StringResponse createNewClipRef(@RequestBody DefaultUrl url) {
        return clientService.createClipRef(url);
    }

    @PostMapping(value = "/get-clip-ref")
    public StringResponse getClipRefByDefRef(@RequestBody DefaultUrl url){
        return clientService.getClipRefByDefRef(url);
    }

    @SneakyThrows
    @GetMapping(value = "/go-to/{clip-ref}")
        public void redirect(@PathVariable("clip-ref") String endpoint, HttpServletResponse response) {
            String redirectUrl = clientService.redirect(endpoint);
            response.sendRedirect(redirectUrl);
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
