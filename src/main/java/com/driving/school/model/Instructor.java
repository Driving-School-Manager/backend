package com.driving.school.model;

import com.driving.school.model.enumeration.LicenseCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    @ElementCollection(targetClass = LicenseCategory.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable
    private Set<LicenseCategory> licenseCategories = new HashSet<>();

    @OneToMany(mappedBy = "instructor")
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToMany
    @JoinTable
    private Set<Vehicle> assignedVehicles = new HashSet<>();

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.PERSIST)
    private Set<InstructorSchedule> instructorSchedules = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private Mailbox mailbox;
}
