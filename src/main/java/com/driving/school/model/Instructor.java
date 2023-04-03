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

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "instructor"
    )
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable
    private Set<Vehicle> assignedVehicles = new HashSet<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "instructor"
    )
    private Set<InstructorSchedule> instructorSchedules = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Mailbox mailbox;
}
