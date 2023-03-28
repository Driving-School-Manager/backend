package com.driving.school.vehicle;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class VehicleDtoMapper implements Function<Vehicle, VehicleDto> {

    @Override
    public VehicleDto apply(Vehicle vehicle) {
        return new VehicleDto(
                vehicle.getBrand(),
                vehicle.getStatus(),
                vehicle.getTransmissionType(),
                vehicle.getYearOfFabrication());
    }
}
