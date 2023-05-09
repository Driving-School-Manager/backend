package com.driving.school.repository;

import com.driving.school.model.MessagePerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessagePerUserRepository extends JpaRepository<MessagePerUser, Long> {

    @Modifying
    @Transactional
    @Query("""
    DELETE FROM MessagePerUser pum WHERE pum.mailbox.id = :id
    """)
    int deleteAllByMailboxId(@Param("id") Long id);

    @Query("""
    SELECT msg FROM MessagePerUser msg
    JOIN FETCH msg.messageBody
    WHERE msg.mailbox.id = :id
    """)
    List<com.driving.school.model.MessagePerUser> findByMailboxId(@Param("id") Long id);

    @Query("""
    SELECT COUNT(msg) FROM MessagePerUser msg
    WHERE msg.messageBody.id = :id
    """)
    long countMessagesAttachedToBodyByBodyId(@Param("id") Long id);
}
