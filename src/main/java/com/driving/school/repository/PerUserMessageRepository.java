package com.driving.school.repository;

import com.driving.school.model.PerUserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PerUserMessageRepository extends JpaRepository<PerUserMessage, Long> {

    @Modifying
    @Transactional
    @Query("""
    DELETE FROM PerUserMessage pum WHERE pum.mailbox.id = :id
    """)
    int deleteAllByMailboxId(@Param("id") Long id);

    @Query("""
    SELECT pum FROM PerUserMessage pum
    JOIN FETCH pum.messageBody
    WHERE pum.mailbox.id = :id
    """)
    List<PerUserMessage> findByMailboxId(@Param("id") Long id);

    @Query("""
    SELECT COUNT(msg) FROM PerUserMessage msg
    WHERE msg.messageBody.id = :id
    """)
    long countMessagesAttachedToBodyByBodyId(@Param("id") Long id);
}
