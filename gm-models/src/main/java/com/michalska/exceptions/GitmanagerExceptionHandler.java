package com.michalska.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GitmanagerExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(RuntimeException runtimeException) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), runtimeException.getMessage());
    }


    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = HttpMediaTypeException.class)
    public ResponseEntity<String> handleNotAcceptableTypeException(HttpMediaTypeNotAcceptableException typeNotAcceptableException) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body("{\n\"status\": " + HttpStatus.NOT_ACCEPTABLE.value() + " \n \"message\": \"Not acceptable accept header\" \n}");
    }
}
