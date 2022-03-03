package ru.thirteenth.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.thirteenth.api.entity.dao.ExceptionDetails;
import ru.thirteenth.api.exception.dao.BlankException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<ExceptionDetails> handleMalformedURLException(
            HttpServletRequest request,
            Exception exception)
    {
        return ResponseEntity.status(BAD_REQUEST).body(new ExceptionDetails(
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                "The value of the 'url' field should be a link",
                BAD_REQUEST.value()
        ));
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionDetails> handleValidException(
            HttpServletRequest request,
            Exception exception)
    {
        return createErrorResponse(request,BAD_REQUEST,exception);
    }

    @ExceptionHandler(BlankException.class)
    public ResponseEntity<ExceptionDetails>handleBlankException(
            HttpServletRequest request,
            Exception exception)
    {
        return createErrorResponse(request,BAD_REQUEST,exception);
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
