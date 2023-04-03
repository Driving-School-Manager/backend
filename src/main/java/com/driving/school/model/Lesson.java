package com.driving.school.model;

import com.driving.school.model.enumeration.LessonStatus;
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
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "lesson"
    )
    private Set<TimeSlot> timeSlots = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private LessonStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Instructor instructor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Vehicle vehicle;

}
