package com.driving.school.model;

import com.driving.school.model.enumeration.LicenseCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String licensePlate;

    private boolean available;

    private String yearOfManufacture;

    @Enumerated(EnumType.STRING)
    private LicenseCategory licenseCategory;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "vehicle"
    )
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToMany(
            mappedBy = "assignedVehicles",
            fetch = FetchType.EAGER
    )
    private Set<Instructor> assignedInstructors = new HashSet<>();
}

