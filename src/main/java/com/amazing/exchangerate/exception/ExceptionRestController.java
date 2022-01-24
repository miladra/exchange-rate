package com.amazing.exchangerate.exception;

import com.amazing.exchangerate.dto.response.BaseWebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;


@RestControllerAdvice
public class ExceptionRestController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseWebResponse> handleEntityNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseWebResponse.error("ENTITY NOT FOUND"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseWebResponse> handleException() {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(BaseWebResponse.error("EXPECTATION OCCURRED"));
    }
}
