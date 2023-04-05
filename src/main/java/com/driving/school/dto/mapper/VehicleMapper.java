package com.driving.school.dto.mapper;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.VehicleCreationDto;
import com.driving.school.dto.VehicleResponseDto;
import com.driving.school.model.Vehicle;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class VehicleMapper implements Mapper<Vehicle> {

    public VehicleResponseDto toResponseDto(Vehicle entity) {
        return new VehicleResponseDto(
                entity.getId(),
                entity.getVehicleName(),
                entity.getLicensePlate(),
                entity.isAvailable(),
                entity.getYearOfManufacture(),
                entity.getLicenseCategory()
        );
    }

    public Vehicle toModel(CreationDto requestData) {
        ifNotInstanceThrow(requestData, VehicleCreationDto.class);

        VehicleCreationDto source = (VehicleCreationDto) requestData;
        return new Vehicle(
                0L,
                source.vehicleName(),
                source.licensePlate(),
                true,
                source.yearOfManufacture(),
                source.licenseCategory(),
                new HashSet<>(),
                new HashSet<>()
        );
    }

}
