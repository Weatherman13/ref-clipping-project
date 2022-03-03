package ru.thirteenth.ref_clipping_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.thirteenth.ref_clipping_service.exception.dao.ClippingRefNotFoundException;
import ru.thirteenth.ref_clipping_service.exception.dao.DefaultRefNotFoundException;
import ru.thirteenth.ref_clipping_service.entity.dao.ExceptionDetails;
import ru.thirteenth.ref_clipping_service.exception.dao.RequestTimeoutExceededException;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DefaultRefNotFoundException.class)
    public ResponseEntity<ExceptionDetails>handleDefaultRefNotFoundException(
            HttpServletRequest request,
            Exception exception)
    {
        return createErrorResponse(request,NOT_ACCEPTABLE,exception);
    }

    @ExceptionHandler(ClippingRefNotFoundException.class)
    public ResponseEntity<ExceptionDetails>handleClippingRefNotFoundException(
            HttpServletRequest request,
            Exception exception)
    {
        return createErrorResponse(request,NOT_ACCEPTABLE,exception);
    }

    @ExceptionHandler(RequestTimeoutExceededException.class)
    public ResponseEntity<ExceptionDetails>handleRequestTimeoutExceededException(
            HttpServletRequest request,
            Exception exception)
    {
        return createErrorResponse(request,SERVICE_UNAVAILABLE,exception);
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
