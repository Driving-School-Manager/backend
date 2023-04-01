package com.driving.school.dto;

public record StudentCreationDto(
        String firstName,
        String lastName,
        String email,
        boolean active
) implements CreationDto { }
