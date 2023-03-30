package com.driving.school.utils;

import com.driving.school.dto.StudentDetailsDTO;
import com.driving.school.dto.StudentListItemDTO;
import com.driving.school.model.StudentModel;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentListItemDTO toStudentListItemDto(StudentModel student) {
        return new StudentListItemDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail());
    }

    public StudentDetailsDTO toStudentDetailsDto(StudentModel student) {
        return new StudentDetailsDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(), student.isActive());
    }

    public StudentModel toStudentModel(StudentDetailsDTO student) {
        return new StudentModel(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.isActive()
        );
    }
}
