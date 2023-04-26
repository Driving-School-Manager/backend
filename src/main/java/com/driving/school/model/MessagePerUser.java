package com.driving.school.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean opened;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private SharedMessageBody messageBody;

    @ManyToOne
    @JoinColumn
    private Mailbox mailbox;
}
