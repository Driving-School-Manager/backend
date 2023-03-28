package com.driving.school.vehicle;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {
    private String brand;
    private String status;
    private String transmissionType;
    private String yearOfFabrication;
}
