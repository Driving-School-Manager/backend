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

    //"name" is a reserved keyword in some SQL dialects
    //using a longer def just to avoid weird problems if we ever migrate to something other than MySQL
    private String vehicleName;

    private String licensePlate;

    private boolean available;

    private String yearOfManufacture;

    @Enumerated(EnumType.STRING)
    private LicenseCategory licenseCategory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicle")
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToMany(mappedBy = "assignedVehicles")
    private Set<Instructor> assignedInstructors = new HashSet<>();
}

