package com.facens.booklist.exception;

import com.couchbase.client.core.error.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.badRequest().body("User Not Found!\n" + ex.getMessage());
    }
}
