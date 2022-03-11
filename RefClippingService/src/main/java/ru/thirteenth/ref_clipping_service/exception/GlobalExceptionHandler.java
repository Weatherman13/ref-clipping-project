package ru.thirteenth.ref_clipping_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.thirteenth.ref_clipping_service.exception.dto.RefNotFoundException;
import ru.thirteenth.ref_clipping_service.entity.dto.ExceptionDetails;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RefNotFoundException.class)
    public ResponseEntity<ExceptionDetails>handleDefaultRefNotFoundException(
            HttpServletRequest request,
            Exception exception)
    {
        return createErrorResponse(request,NOT_ACCEPTABLE,exception);
    }




    private ResponseEntity<ExceptionDetails> createErrorResponse(
            HttpServletRequest request,
            HttpStatus status,
            Exception exception)
    {
        return ResponseEntity.status(status).body(new ExceptionDetails(
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                exception.getLocalizedMessage(),
                status.value()));
    }
}
