package com.driving.school.dto;

public record InstructorResponseDto(
        long id,
        String firstName,
        String lastName,
        String email
) implements ResponseDto{}