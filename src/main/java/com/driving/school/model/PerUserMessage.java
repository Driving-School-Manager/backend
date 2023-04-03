package com.driving.school.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerUserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean opened;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private SharedMessageBody messageBody;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Mailbox mailbox;
}
