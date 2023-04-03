package com.driving.school.service;

import com.driving.school.dto.mapper.StudentMapper;
import com.driving.school.model.Student;
import com.driving.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends CrudService<Student> {
    @Autowired
    public StudentService(StudentRepository repo, StudentMapper mapper) {
        super(repo, mapper);
    }

    @Override
    protected String getNotFoundExceptionTemplate() {
        return "Student ID %d was not found in the database.";
    }
}
