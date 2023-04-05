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

    @OneToMany(mappedBy = "student", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Lesson> lessons = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Payment> payments = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Mailbox mailbox;

    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setStudent(this);
    }

    public void removePayment(Payment payment) {
        payment.setStudent(null);
        this.payments.remove(payment);
    }
}
