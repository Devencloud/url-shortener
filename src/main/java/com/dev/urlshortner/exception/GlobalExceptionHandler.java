package com.dev.urlshortner.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Return a 400 Bad Request instead of crashing/looping
        return ResponseEntity.badRequest().body("Validation Failed: " +
                ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(org.springframework.data.redis.RedisConnectionFailureException.class)
    public ResponseEntity<String> handleRedisConnectionFailure(Exception ex) {
        return ResponseEntity.status(503).body("Service temporarily unavailable, please try again later.");
    }

}