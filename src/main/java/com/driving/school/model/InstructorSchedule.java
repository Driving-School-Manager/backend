package com.driving.school.model;

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
public class InstructorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Timetable timetable;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Instructor instructor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instructorSchedule")
    private Set<TimeSlot> timeSlots = new HashSet<>();

}
