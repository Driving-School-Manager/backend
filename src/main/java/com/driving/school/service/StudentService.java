package com.driving.school.service;

import com.driving.school.dto.StudentCreationDto;
import com.driving.school.dto.StudentResponseDto;
import com.driving.school.exception.StudentNotFoundException;
import com.driving.school.model.Student;
import com.driving.school.repository.StudentRepository;
import com.driving.school.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repo;
    private final StudentMapper mapper;

    public List<StudentResponseDto> getStudents() {
        return repo.findAll().stream()
                .map(mapper::toStudentResponseDto)
                .toList();
    }

    public StudentResponseDto getStudentById(long id) {
        return repo.findById(id)
                .map(mapper::toStudentResponseDto)
                .orElseThrow(StudentNotFoundException.supplyFrom(id));
    }

    public void deleteStudentById(long id) {
        int deletedRows = repo.removeById(id);
        if (deletedRows != 1) {
            throw StudentNotFoundException.supplyFrom(id).get();
        }
    }

    public StudentResponseDto addStudent(StudentCreationDto studentRequestDto) {
        Student newStudentModel = mapper.toStudent(studentRequestDto);
        // we should use the return value for further processing
        // it's not the same as newStudentModel (it has an ID, for example)
        Student created = repo.save(newStudentModel);

        return mapper.toStudentResponseDto(created);
    }

    public StudentResponseDto updateStudent(long id, StudentCreationDto student) {
        deleteStudentById(id);

        return addStudent(student);
    }

}
