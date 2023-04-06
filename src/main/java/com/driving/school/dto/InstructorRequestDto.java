package com.driving.school.dto;

public record InstructorRequestDto(
        String firstName,
        String lastName,
        String email

) implements CreationDto, UpdateDto { }
