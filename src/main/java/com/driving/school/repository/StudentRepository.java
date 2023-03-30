package com.driving.school.repository;

import com.driving.school.model.StudentModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    @Transactional
    int removeById(Long id);

}
