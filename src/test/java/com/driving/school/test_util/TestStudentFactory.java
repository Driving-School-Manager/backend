package com.driving.school.test_util;

import com.driving.school.dto.ResponseDto;
import com.driving.school.dto.StudentCreationDto;
import com.driving.school.dto.StudentUpdateDto;
import com.driving.school.dto.mapper.StudentMapper;
import com.driving.school.dto.mapper.StudentMapperImpl;
import com.driving.school.model.Mailbox;
import com.driving.school.model.Student;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

public class TestStudentFactory {
    public static Student createStudent() {
        return new Student(
                1L,
                "Anna",
                "Wiśniewska",
                "anna@wisniewska.com",
                false,
                false,
                BigDecimal.ZERO,
                1800,
                new HashSet<>(),
                new HashSet<>(),
                new Mailbox()
        );
    }

    public static List<ResponseDto> createExpectedMappings(List<Student> students) {
        StudentMapper mapper = new StudentMapperImpl();
        return students.stream()
                .map(mapper::toResponseDto)
                .map(dto -> (ResponseDto) dto)
                .toList();
    }

    public static ResponseDto createExpectedMapping(Student student) {
        StudentMapper mapper = new StudentMapperImpl();
        return mapper.toResponseDto(student);
    }

    public static ResponseDto createExpectedMapping(long id) {
        Student student = createStudent();
        student.setId(id);
        return createExpectedMapping(student);
    }

    public static StudentUpdateDto createUpdateDto() {
        return createUpdateDto(createStudent());
    }

    public static StudentUpdateDto createUpdateDto(Student studentAfterUpdate) {
        return new StudentUpdateDto(
                studentAfterUpdate.getFirstName(),
                studentAfterUpdate.getLastName(),
                studentAfterUpdate.getEmail(),
                studentAfterUpdate.isBlocked(),
                studentAfterUpdate.isMarketingEnabled(),
                studentAfterUpdate.getAccountBalance(),
                studentAfterUpdate.getLessonMinutesLeft()
        );
    }

    public static StudentCreationDto createCreationDto(Student student) {
        return new StudentCreationDto(
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.isMarketingEnabled()
        );
    }
}
