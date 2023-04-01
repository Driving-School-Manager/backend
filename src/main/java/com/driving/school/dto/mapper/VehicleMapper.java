package com.driving.school.dto.mapper;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.VehicleCreationDto;
import com.driving.school.dto.VehicleResponseDto;
import com.driving.school.model.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper implements Mapper<Vehicle> {

    public VehicleResponseDto toResponseDto(Vehicle source) {
        return new VehicleResponseDto(
                source.getId(),
                source.getBrand(),
                source.getStatus(),
                source.getTransmission(),
                source.getYearOfManufacture()
        );
    }

    public Vehicle toModel(CreationDto requestData) {
        VehicleCreationDto source = (VehicleCreationDto) requestData;
        return new Vehicle(
                source.brand(),
                source.status(),
                source.transmission(),
                source.yearOfManufacture()
        );
    }
}
