package com.driving.school.repository;

import com.driving.school.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Transactional
    @Modifying
    @Query("""
    UPDATE Lesson l SET l.student = null WHERE l.student.id = :id
    """)
    int nullStudentColumnByStudentId(@Param("id") Long id);
}
