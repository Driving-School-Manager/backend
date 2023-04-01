package com.driving.school.config.web;

import com.driving.school.exception.StudentNotFoundException;
import com.driving.school.exception.VehicleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            StudentNotFoundException.class,
            VehicleNotFoundException.class
    })
    public ResponseEntity<String> handleStudentNotFound(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
