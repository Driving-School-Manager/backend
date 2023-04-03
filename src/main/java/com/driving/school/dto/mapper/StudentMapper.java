package com.driving.school.dto.mapper;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.StudentCreationDto;
import com.driving.school.dto.StudentResponseDto;
import com.driving.school.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements Mapper<Student> {
    public StudentResponseDto toResponseDto(Student entity) {
        return new StudentResponseDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.isActive()
        );
    }

    public Student toModel(CreationDto requestData) {
        ifNotInstanceThrow(requestData, StudentCreationDto.class);

        StudentCreationDto source = (StudentCreationDto) requestData;
        return new Student(
                0L,
                source.firstName(),
                source.lastName(),
                source.email(),
                source.active()
        );
    }
}
