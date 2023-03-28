package com.driving.school.vehicle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.function.EntityResponse;

@ControllerAdvice("vehicle")
public class VehicleControllerAdvice {

    @ExceptionHandler(NoVehicleFoundException.class)
    public ResponseEntity<String> handleNoVehicleFoundException(NoVehicleFoundException ex) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);

    }
}
