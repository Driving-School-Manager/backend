package com.driving.school.dto.mapper;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.StudentCreationDto;
import com.driving.school.dto.StudentResponseDto;
import com.driving.school.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements Mapper<Student> {
    public StudentResponseDto toResponseDto(Student source) {
        return new StudentResponseDto(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getEmail(),
                source.isActive()
        );
    }

    public Student toModel(CreationDto requestData) {
        StudentCreationDto source = (StudentCreationDto) requestData;
        return new Student(
                source.firstName(),
                source.lastName(),
                source.email(),
                source.active()
        );
    }
}
