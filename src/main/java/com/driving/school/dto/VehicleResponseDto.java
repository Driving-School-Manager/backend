package com.driving.school.dto;

public record VehicleResponseDto(
        long id,
        String brand,
        String status,
        String transmission,
        String yearOfManufacture
) implements ResponseDto { }
