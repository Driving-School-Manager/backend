package com.driving.school.dto;

import com.driving.school.model.enumeration.LicenseCategory;

public record VehicleUpdateDto(
        String vehicleName,
        String licensePlate,
        boolean available,
        String yearOfManufacture,
        LicenseCategory licenseCategory

) implements UpdateDto  { }
