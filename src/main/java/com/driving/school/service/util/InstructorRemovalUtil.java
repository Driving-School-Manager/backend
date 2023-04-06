package com.driving.school.service.util;

import com.driving.school.model.Instructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstructorRemovalUtil implements RemovalUtil<Instructor> {
    // TODO this is actually a pretty complex operation
    // TODO we need to decide what to do with Lessons etc. before we attempt to implement it
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteEntity(Instructor instructor) {
//        long instructorId = instructor.getId();
//        log.info("Deleting Instructor with ID {}...", instructorId);

        throw new UnsupportedOperationException("Deleting Instructors is broken right now");
    }
}
