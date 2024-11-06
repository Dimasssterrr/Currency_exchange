package ru.skillbox.currency.exchange.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handel (RuntimeException ex) {
        ApiError apiError = new ApiError().badRequest(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

}
