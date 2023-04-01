package com.driving.school.dto;

public record VehicleCreationDto(
        String brand,
        String status,
        String transmission,
        String yearOfManufacture
) implements CreationDto { }
