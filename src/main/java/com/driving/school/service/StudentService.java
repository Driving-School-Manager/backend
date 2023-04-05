package com.driving.school.service;

import com.driving.school.dto.mapper.StudentMapper;
import com.driving.school.model.Student;
import com.driving.school.repository.StudentRepository;
import com.driving.school.service.util.StudentRemovalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService extends CrudService<Student> {
    private final StudentRemovalUtil removalUtil;

    @Autowired
    public StudentService(
            StudentRepository repo,
            StudentMapper mapper,
            StudentRemovalUtil removalUtil
    ) {
        super(repo, mapper);
        this.removalUtil = removalUtil;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Student student = repo.findById(id)
                .orElseThrow(() -> buildException(id));

        removalUtil.deleteStudent(student);
    }
}
