package com.driving.school.repository;

import com.driving.school.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Modifying
    @Transactional
    @Query("""
    DELETE FROM Payment p WHERE p.student.id = :id
    """)
    int deleteAllByStudentId(@Param("id") Long id);

}
