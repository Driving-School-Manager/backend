package com.driving.school.exception;

import java.util.function.Supplier;

public class VehicleNotFoundException extends RuntimeException {
    private VehicleNotFoundException(String message) {
        super(message);
    }

    public static Supplier<VehicleNotFoundException> supplyFrom(long id) {
        String msg = "Vehicle ID " + id + " does not exist in the database";
        return () -> new VehicleNotFoundException(msg);
    }
}
