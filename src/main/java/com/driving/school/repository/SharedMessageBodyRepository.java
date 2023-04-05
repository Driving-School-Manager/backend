package com.driving.school.repository;

import com.driving.school.model.SharedMessageBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface SharedMessageBodyRepository extends JpaRepository<SharedMessageBody, Long> {

    @Modifying
    @Transactional
    @Query("""
    DELETE FROM SharedMessageBody body WHERE body.id IN :ids
    """)
    int deleteAllByIds(@Param("ids") Collection<Long> ids);
}
