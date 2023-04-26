package com.driving.school.model;

import com.driving.school.model.enumeration.DayOfWeek;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //"date" is a reserved keyword in some SQL dialects
    //using a longer def just to avoid weird problems if we ever migrate to something other than MySQL
    private LocalDate timetableDate;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL)
    private Set<InstructorSchedule> instructorSchedules = new HashSet<>();
}
