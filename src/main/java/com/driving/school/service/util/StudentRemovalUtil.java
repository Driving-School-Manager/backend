package com.driving.school.service.util;

import com.driving.school.model.PerUserMessage;
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
public class StudentRemovalUtil {
    private final StudentRepository studentRepo;
    private final PaymentRepository paymentRepo;
    private final LessonRepository lessonRepo;
    private final MailboxRepository mailboxRepository;
    private final PerUserMessageRepository userMessageRepo;
    private final SharedMessageBodyRepository sharedMessageBodyRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteStudent(Student student) {
        long studentId = student.getId();
        log.info("Deleting Student with ID {}...", studentId);

        deleteAssociatedPayments(studentId);
        nullAssociatedLessons(studentId);

        long mailboxId = findAndClearAssociatedMailbox(student);

        deleteStudent(studentId);

        deleteMailbox(mailboxId);
    }

    private void deleteAssociatedPayments(long studentId) {
        int deletedPaymentsCount = paymentRepo.deleteAllByStudentId(studentId);
        log.info("\t* Found and removed {} associated Payments ", deletedPaymentsCount);
    }

    private void nullAssociatedLessons(long studentId) {
        int changedLessonsCount = lessonRepo.nullStudentColumnByStudentId(studentId);
        if (changedLessonsCount != 0) {
            log.warn("\t* Found {} associated Lessons that could not be deleted, Student column set to NULL instead",
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
        List<PerUserMessage> messages = findUserMessages(mailboxId);

        List<Long> bodyIdsMarkedForRemoval = findPotentiallyOrphanedMessageBodies(messages);

        deleteUserMessages(mailboxId);

        deleteOrphanedMessageBodies(bodyIdsMarkedForRemoval);
    }

    private List<PerUserMessage> findUserMessages(long mailboxId) {
        List<PerUserMessage> messages = userMessageRepo.findByMailboxId(mailboxId);
        log.info("\t* Found {} associated Messages, checking for potentially orphaned MessageBodies", messages.size());
        return messages;
    }

    private List<Long> findPotentiallyOrphanedMessageBodies(List<PerUserMessage> messages) {
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

    private void deleteStudent(long studentId) {
        studentRepo.deleteById(studentId);
        log.info("Student deleted.");
    }

    private void deleteMailbox(long mailboxId) {
        mailboxRepository.deleteById(mailboxId);
        log.info("Mailbox deleted.");
    }
}
