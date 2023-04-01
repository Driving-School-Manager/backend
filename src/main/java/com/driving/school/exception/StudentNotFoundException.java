package com.driving.school.exception;

import java.util.function.Supplier;

public class StudentNotFoundException extends RuntimeException {
    private StudentNotFoundException(String message) {
        super(message);
    }

    public static Supplier<StudentNotFoundException> supplyFrom(long id) {
        String msg = "Student ID " + id + " does not exist in the database";
        return () -> new StudentNotFoundException(msg);
    }
}
