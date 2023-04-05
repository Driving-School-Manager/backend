package com.driving.school.dto;

import java.math.BigDecimal;

public record StudentResponseDto(
        long id,
        String firstName,
        String lastName,
        String email,
        boolean blocked,
        boolean marketingEnabled,
        BigDecimal accountBalance,
        int lessonMinutesLeft

) implements ResponseDto { }
