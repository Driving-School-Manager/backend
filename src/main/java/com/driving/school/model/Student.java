package com.driving.school.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private boolean blocked;

    private boolean marketingEnabled;

    private BigDecimal accountBalance;

    private int lessonMinutesLeft;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "student"
    )
    private Set<Lesson> lessons = new HashSet<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "student"
    )
    private Set<Payment> payments = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Mailbox mailbox;
}
