package com.driving.school.service;

import com.driving.school.model.StudentModel;
import com.driving.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repo;
    public List<StudentModel> getStudents(){
        return repo.findAll();
    }

    public StudentModel getStudentById(long id){
        return repo.findById(id).orElseThrow(() -> new InvalidConfigurationPropertyValueException("id",id, "student with given id not found in data base"));
    }
}
