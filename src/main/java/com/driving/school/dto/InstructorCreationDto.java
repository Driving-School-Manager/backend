package com.driving.school.dto;

public record InstructorCreationDto(
        String firstName,
        String lastName,
        String email
) implements CreationDto{}
