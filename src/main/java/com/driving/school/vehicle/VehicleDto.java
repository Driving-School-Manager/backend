package com.driving.school.vehicle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
public class VehicleDto {
    private String brand;
    private String status;
    private VehicleTransmissionType type;
    private String yearOfFabrication;
}
