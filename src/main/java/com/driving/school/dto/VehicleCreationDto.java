package com.driving.school.dto;

public record VehicleCreationDto(
        String name,
        boolean available,
        String yearOfManufacture
) implements CreationDto { }
