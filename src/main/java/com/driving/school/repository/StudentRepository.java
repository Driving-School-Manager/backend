package com.driving.school.repository;

import com.driving.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Override
    @Modifying
    @Transactional
    @Query("""
    DELETE FROM Student s WHERE s.id = :id
    """)
    void deleteById(@Param("id") Long id);
}
