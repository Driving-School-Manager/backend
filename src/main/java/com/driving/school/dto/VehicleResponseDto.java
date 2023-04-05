package com.driving.school.dto;

import com.driving.school.model.enumeration.LicenseCategory;

public record VehicleResponseDto(
        long id,
        String name,
        String licensePlate,
        boolean available,
        String yearOfManufacture,
        LicenseCategory licenseCategory

) implements ResponseDto { }
