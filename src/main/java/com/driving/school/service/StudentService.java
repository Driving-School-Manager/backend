package com.driving.school.service;

import com.driving.school.dto.StudentDetailsDTO;
import com.driving.school.dto.StudentListItemDTO;
import com.driving.school.repository.StudentRepository;
import com.driving.school.utils.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repo;
    private final StudentMapper mapper;

    public List<StudentListItemDTO> getStudents() {
        return repo.findAll().stream().map(mapper::toStudentListItemDto).toList();
    }

    public StudentDetailsDTO getStudentById(long id) {
        return repo.findById(id).map(mapper::toStudentDetailsDto).orElseThrow(() -> new InvalidConfigurationPropertyValueException("id", id, "student with given id not found in data base"));
    }

    public void deleteStudentById(long id) {
        int deletedRows = repo.removeById(id);
        if (deletedRows != 1) {
            throw new InvalidConfigurationPropertyValueException("id", id, "student with given id not found in data base");
        }
    }

    public void addStudent(StudentDetailsDTO student) {
        repo.save(mapper.toStudentModel(student));
    }

    public void updateStudent(long id, StudentDetailsDTO student) {
        deleteStudentById(id);
        addStudent(student);
    }

}
