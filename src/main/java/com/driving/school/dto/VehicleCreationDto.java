package com.driving.school.dto;

import com.driving.school.model.enumeration.LicenseCategory;

public record VehicleCreationDto(
        String vehicleName,
        String licensePlate,
        String yearOfManufacture,
        LicenseCategory licenseCategory

) implements CreationDto { }
