package com.driving.school.dto;

public record VehicleCreationDto(
        String brand,
        boolean active,
        String yearOfManufacture
) implements CreationDto { }
