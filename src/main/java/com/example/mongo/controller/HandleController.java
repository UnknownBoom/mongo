package com.example.mongo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
