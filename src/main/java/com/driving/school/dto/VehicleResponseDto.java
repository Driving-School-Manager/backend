package com.driving.school.dto;

public record VehicleResponseDto(
        Long id,
        String brand,
        String status,
        String transmission,
        String yearOfManufacture
) {
}
