package com.driving.school.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Vehicle extends BaseModel {
    @Column(name = "brand")
    private String brand;
    @Column(name = "status")
    private String status;
    @Column(name = "transmission")
    private String transmission;
    @Column(name = "year_of_manufacture")
    private String yearOfManufacture;
}

