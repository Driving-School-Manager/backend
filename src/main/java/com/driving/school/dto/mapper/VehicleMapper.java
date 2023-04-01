package com.driving.school.dto.mapper;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.VehicleCreationDto;
import com.driving.school.dto.VehicleResponseDto;
import com.driving.school.model.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper implements Mapper<Vehicle> {

    public VehicleResponseDto toResponseDto(Vehicle entity) {
        return new VehicleResponseDto(
                entity.getId(),
                entity.getBrand(),
                entity.getStatus(),
                entity.getTransmission(),
                entity.getYearOfManufacture()
        );
    }

    public Vehicle toModel(CreationDto requestData) {
        if (requestData instanceof VehicleCreationDto source) {
            return new Vehicle(
                    source.brand(),
                    source.status(),
                    source.transmission(),
                    source.yearOfManufacture()
            );
        }

        throw throwOnInvalidRequestType(requestData);
    }

}
