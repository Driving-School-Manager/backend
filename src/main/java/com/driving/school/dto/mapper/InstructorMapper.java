package com.driving.school.dto.mapper;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.InstructorCreationDto;
import com.driving.school.dto.InstructorResponseDto;
import com.driving.school.model.Instructor;
import com.driving.school.model.Mailbox;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class InstructorMapper implements Mapper<Instructor> {
    @Override
    public InstructorResponseDto toResponseDto(Instructor entity) {
        return new InstructorResponseDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail()
        );
    }

    @Override
    public Instructor toModel(CreationDto requestData) {
        ifNotInstanceThrow(requestData, InstructorCreationDto.class);

        InstructorCreationDto source = (InstructorCreationDto) requestData;
        return new Instructor(
                0L,
                source.firstName(),
                source.lastName(),
                source.email(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new Mailbox()
                );

    }

}
