package com.driving.school.repository;

import com.driving.school.model.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MailboxRepository extends JpaRepository<Mailbox, Long> {

    @Override
    @Modifying
    @Transactional
    @Query("""
    DELETE FROM Mailbox m WHERE m.id = :id
    """)
    void deleteById(@Param("id") Long id);

}
