package com.driving.school.dto;

import java.math.BigDecimal;

public record StudentUpdateDto(
        String firstName,
        String lastName,
        String email,
        Boolean blocked,
        Boolean marketingEnabled,
        BigDecimal accountBalance,
        Integer lessonMinutesLeft
) implements UpdateDto { }
