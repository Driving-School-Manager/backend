package com.driving.school.vehicle;

public class NoVehicleFoundException extends RuntimeException {
    public NoVehicleFoundException(String message) {
        super(message);
    }
}
