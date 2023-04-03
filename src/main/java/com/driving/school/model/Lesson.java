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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lesson")
    private Set<TimeSlot> timeSlots = new HashSet<>();

    //"status" is a reserved keyword in some SQL dialects
    //using a longer def just to avoid weird problems if we ever migrate to something other than MySQL
    @Enumerated(EnumType.STRING)
    private LessonStatus lessonStatus;

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
