package com.driving.school.dto.mapper;

import com.driving.school.dto.VehicleCreationDto;
import com.driving.school.dto.VehicleResponseDto;
import com.driving.school.model.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public VehicleResponseDto toResponseDto(Vehicle source) {
        return new VehicleResponseDto(
                source.getId(),
                source.getBrand(),
                source.getStatus(),
                source.getTransmission(),
                source.getYearOfManufacture()
        );
    }

    public Vehicle toModel(VehicleCreationDto source) {
        return new Vehicle(
                0L,
                source.brand(),
                source.status(),
                source.transmission(),
                source.yearOfManufacture()
        );
    }
}
