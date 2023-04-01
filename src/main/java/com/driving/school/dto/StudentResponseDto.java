package com.driving.school.dto;

public record StudentResponseDto(
        long id,
        String firstName,
        String lastName,
        String email,
        boolean active
) { }
