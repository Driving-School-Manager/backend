package com.driving.school.dto;

public record VehicleResponseDto(
        long id,
        String brand,
        boolean active,
        String yearOfManufacture
) implements ResponseDto { }
