package com.driving.school.model;

import com.driving.school.model.enumeration.InstructorAvailability;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //"start" / "end" are a reserved keywords in some SQL dialects
    //using longer defs just to avoid weird problems if we ever migrate to something other than MySQL
    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JoinColumn
    private InstructorSchedule instructorSchedule;

    @Enumerated(EnumType.STRING)
    private InstructorAvailability availability;

    @ManyToOne
    @JoinColumn
    @Nullable
    private Lesson lesson;
}
