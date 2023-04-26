package com.driving.school.dto;

import com.driving.school.model.enumeration.LicenseCategory;

public record VehicleRequestDto(
        String vehicleName,
        String licensePlate,
        String yearOfManufacture,
        LicenseCategory licenseCategory

) implements CreationDto, UpdateDto { }
