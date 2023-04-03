package com.driving.school.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedMessageBody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Instant issuedAt;

    private Instant expiresAt;

    private String title;

    @Lob
    private byte[] compressedData;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "messageBody")
    private Set<PerUserMessage> messages;
}
