package com.driving.school.service;

import com.driving.school.model.StudentModel;
import com.driving.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repo;
    public List<StudentModel> getStudents(){
        return repo.findAll();
    }

    public Optional<StudentModel> getStudentById(long id){
        return repo.findById(id);
    }
}
