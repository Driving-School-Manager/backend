package com.driving.school.service.util;

import com.driving.school.model.Student;
import com.driving.school.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentRemovalUtil implements RemovalUtil<Student> {
    private final StudentRepository studentRepo;
    private final PaymentRepository paymentRepo;
    private final LessonRepository lessonRepo;
    private final MailboxRepository mailboxRepository;
    private final MessagePerUser userMessageRepo;
    private final SharedMessageBodyRepository sharedMessageBodyRepository;


    /**
     * Deletes a Student and:<br>
     * 1. deletes all Payments associated with that Student<br>
     * 2. if there are any Lessons associated with the Student, this method WILL NOT delete them and instead set
     * their 'student_id' to NULL<br>
     * 3. deletes all (individual) PerUserMessages associated with the Student. If a given MessagePerUser is the LAST
     * ONE with a given SharedMessageBody, the SharedMessageBody is also deleted<br>
     * 4. deletes the Mailbox associated with the Student<br>
     * @param   student
     *          The Student entity, which is assumed to exist at this point.
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteEntity(Student student) {
        long studentId = student.getId();
        log.info("Deleting Student with ID {}...", studentId);

        deleteAssociatedPayments(studentId);
        // TODO decide what to do with Lessons when deleting the Student
        nullAssociatedLessons(studentId);

        long mailboxId = findAndClearAssociatedMailbox(student);

        deleteEntityById(studentId);

        deleteMailbox(mailboxId);
    }

    private void deleteAssociatedPayments(long studentId) {
        int deletedPaymentsCount = paymentRepo.deleteAllByStudentId(studentId);
        log.info("\t* Found and removed {} associated Payments ", deletedPaymentsCount);
    }

    private void nullAssociatedLessons(long studentId) {
        int changedLessonsCount = lessonRepo.nullStudentColumnByStudentId(studentId);
        if (changedLessonsCount != 0) {
            log.warn("\t* Found {} associated Lessons that could not be deleted, 'student_id' column set to NULL instead",
                    changedLessonsCount);
        } else {
            log.info("\t* No Lessons were associated with this Student");
        }
    }

    private long findAndClearAssociatedMailbox(Student student) {
        long mailboxId = student.getMailbox().getId();

        clearMailbox(mailboxId);

        return mailboxId;
    }

    private void clearMailbox(long mailboxId) {
        List<com.driving.school.model.MessagePerUser> messages = findUserMessages(mailboxId);

        List<Long> bodyIdsMarkedForRemoval = findPotentiallyOrphanedMessageBodies(messages);

        deleteUserMessages(mailboxId);

        deleteOrphanedMessageBodies(bodyIdsMarkedForRemoval);
    }

    private List<com.driving.school.model.MessagePerUser> findUserMessages(long mailboxId) {
        List<com.driving.school.model.MessagePerUser> messages = userMessageRepo.findByMailboxId(mailboxId);
        log.info("\t* Found {} associated Messages, checking for potentially orphaned MessageBodies", messages.size());
        return messages;
    }

    private List<Long> findPotentiallyOrphanedMessageBodies(List<com.driving.school.model.MessagePerUser> messages) {
        return messages.stream()
                .map(msg -> msg.getMessageBody().getId())
                .filter(this::willBodyBecomeOrphanedAfterMessageIsRemoved)
                .peek(bodyId ->
                        log.info("\t* MessageBody ID {} has no other associated Messages; marking for removal", bodyId))
                .toList();

    }

    private boolean willBodyBecomeOrphanedAfterMessageIsRemoved(long bodyId) {
        long numberOfMessagesWithThisBody = userMessageRepo.countMessagesAttachedToBodyByBodyId(bodyId);
        return numberOfMessagesWithThisBody == 1;
    }

    private void deleteUserMessages(long mailboxId) {
        int deletedMessagesCount = userMessageRepo.deleteAllByMailboxId(mailboxId);
        log.info("\t* Deleted {} associated Messages", deletedMessagesCount);
    }

    private void deleteOrphanedMessageBodies(List<Long> bodyIdsMarkedForRemoval) {
        int deletedBodiesCount = sharedMessageBodyRepository.deleteAllByIds(bodyIdsMarkedForRemoval);
        log.info("\t* Deleted {} orphaned Message Bodies", deletedBodiesCount);
    }

    private void deleteEntityById(long studentId) {
        studentRepo.deleteById(studentId);
        log.info("Student deleted.");
    }

    private void deleteMailbox(long mailboxId) {
        mailboxRepository.deleteById(mailboxId);
        log.info("Mailbox deleted.");
    }
}
