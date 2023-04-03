package com.driving.school.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerUserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Instant issuedAt;

    private Instant expiresAt;

    private boolean opened;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private SharedMessageBody messageBody;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Mailbox mailbox;
}
