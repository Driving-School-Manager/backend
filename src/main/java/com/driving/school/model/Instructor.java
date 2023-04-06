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

    @OneToMany(mappedBy = "instructor", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToMany
    @JoinTable
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Vehicle> assignedVehicles = new HashSet<>();

    @OneToMany(mappedBy = "instructor", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<InstructorSchedule> instructorSchedules = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Mailbox mailbox = new Mailbox();
}
