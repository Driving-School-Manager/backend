package com.driving.school.dto;

import com.driving.school.model.Lesson;
import com.driving.school.model.Payment;
import com.driving.school.model.PerUserMessage;

import java.math.BigDecimal;
import java.util.Set;

public record StudentResponseDto(
        long id,
        String firstName,
        String lastName,
        String email,
        boolean blocked,
        boolean wantsMarketing,
        BigDecimal accountBalance,
        int lessonMinutesLeft,
        Set<Lesson> lessons,
        Set<Payment> payments,

        Set<PerUserMessage> messages

) implements ResponseDto { }
