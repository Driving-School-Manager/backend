package com.driving.school.dto.mapper;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.VehicleCreationDto;
import com.driving.school.dto.VehicleResponseDto;
import com.driving.school.model.Vehicle;
import com.driving.school.model.enumeration.LicenseCategory;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class VehicleMapper implements Mapper<Vehicle> {

    public VehicleResponseDto toResponseDto(Vehicle entity) {
        return new VehicleResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getLicensePlate(),
                entity.isAvailable(),
                entity.getYearOfManufacture(),
                entity.getLicenseCategory(),
                entity.getLessons(),
                entity.getAssignedInstructors()
        );
    }

    public Vehicle toModel(CreationDto requestData) {
        ifNotInstanceThrow(requestData, VehicleCreationDto.class);

        VehicleCreationDto source = (VehicleCreationDto) requestData;
        return new Vehicle(
                0L,
                source.name(),
                "LICENSE PLATE",
                source.available(),
                source.yearOfManufacture(),
                LicenseCategory.B,
                new HashSet<>(),
                new HashSet<>()
        );
    }

}
