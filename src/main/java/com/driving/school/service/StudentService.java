package com.driving.school.service;

import com.driving.school.dto.StudentCreationDto;
import com.driving.school.dto.StudentResponseDto;
import com.driving.school.dto.mapper.StudentMapper;
import com.driving.school.exception.StudentNotFoundException;
import com.driving.school.model.Student;
import com.driving.school.repository.StudentRepository;
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
                .map(mapper::toResponseDto)
                .toList();
    }

    public StudentResponseDto getStudentById(long id) {
        return repo.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(StudentNotFoundException.supplyFrom(id));
    }

    public StudentResponseDto addStudent(StudentCreationDto requestData) {
        Student newStudentModel = mapper.toModel(requestData);
        // we should use the return value for further processing
        // it's not the same as newStudentModel (it has an ID, for example)
        Student created = repo.save(newStudentModel);

        return mapper.toResponseDto(created);
    }

    public void deleteStudentById(long id) {
        throwIfNotInDatabase(id);
        repo.deleteById(id);
    }

    public StudentResponseDto replaceStudent(long id, StudentCreationDto requestData) {
        deleteStudentById(id);

        return addStudent(requestData);
    }

    private void throwIfNotInDatabase(Long id) {
        if (!repo.existsById(id)) {
            throw StudentNotFoundException.supplyFrom(id).get();
        }
    }

}
