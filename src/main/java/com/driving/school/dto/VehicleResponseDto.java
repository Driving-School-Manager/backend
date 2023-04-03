package com.driving.school.dto;

import com.driving.school.model.Instructor;
import com.driving.school.model.Lesson;
import com.driving.school.model.enumeration.LicenseCategory;

import java.util.Set;

public record VehicleResponseDto(
        long id,
        String name,
        String licensePlate,
        boolean available,
        String yearOfManufacture,
        LicenseCategory licenseCategory,
        Set<Lesson> lessons,
        Set<Instructor> assignedInstructors

) implements ResponseDto { }
