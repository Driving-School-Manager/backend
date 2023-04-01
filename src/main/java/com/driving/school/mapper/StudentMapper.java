package com.driving.school.mapper;

import com.driving.school.dto.StudentCreationDto;
import com.driving.school.dto.StudentResponseDto;
import com.driving.school.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentResponseDto toStudentResponseDto(Student source) {
        return new StudentResponseDto(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getEmail(),
                source.isActive()
        );
    }

    public Student toStudent(StudentCreationDto source) {
        return new Student(
                0L,
                source.firstName(),
                source.lastName(),
                source.email(),
                source.active()
        );
    }
}
