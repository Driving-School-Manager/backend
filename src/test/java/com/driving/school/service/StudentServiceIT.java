package com.driving.school.service;

import com.driving.school.dto.ResponseDto;
import com.driving.school.dto.StudentCreationDto;
import com.driving.school.dto.StudentUpdateDto;
import com.driving.school.exception.ResourceNotFoundException;
import com.driving.school.model.Mailbox;
import com.driving.school.model.Student;
import com.driving.school.repository.MailboxRepository;
import com.driving.school.repository.StudentRepository;
import com.driving.school.test_util.TestStudentFactory;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StudentServiceIT {
    @Autowired
    private StudentService service;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private MailboxRepository mailboxRepo;

    @Test
    @DisplayName("getAll(): Gets a list of students")
    @Sql(value = "classpath:testSql/student-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void whenGetAll_thenOK() {
        //given
        List<Student> allStudents = studentRepo.findAll();
        confirmSqlWasExecutedCorrectly();
        //and
        List<ResponseDto> expectedMappings = TestStudentFactory.createExpectedMappings(allStudents);

        //when
        List<ResponseDto> actual = service.getAll();

        //then
        assertAllDtosAreCorrect(expectedMappings, actual);
    }

    @Test
    @DisplayName("getById(): Successfully gets an entity")
    @Sql(value = "classpath:testSql/student-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void whenGetById_thenOK() {
        //given
        long id = 2L;
        Student existingStudent = studentRepo.findById(id).orElseThrow();
        confirmSqlWasExecutedCorrectly();
        //and
        ResponseDto expected = TestStudentFactory.createExpectedMapping(existingStudent);

        //when
        ResponseDto actual = service.getById(id);

        //then
        assertCorrectDtoIsReturned(expected, actual);
    }

    @Test
    @DisplayName("patchById(): Updates an existing entity")
    @Sql(value = "classpath:testSql/student-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void whenPatchById_thenOK() {
        //given
        long id = 2L;
        confirmSqlWasExecutedCorrectly();
        //and
        long initialStudentCount = studentRepo.count();
        //and
        StudentUpdateDto requestData = TestStudentFactory.createUpdateDto();
        ResponseDto expectedResponse = TestStudentFactory.createExpectedMapping(id);

        //when
        ResponseDto actual = service.patchById(id, requestData);

        //then
        assertCorrectDtoIsReturned(expectedResponse, actual);
        assertStudentWasUpdatedCorrectly(id);
        assertStudentCountDidNotChange(initialStudentCount);
    }

    @Test
    @DisplayName("deleteById(): Deletes an existing entity")
    @Sql(value = "classpath:testSql/student-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void whenDeleteById_thenOK() {
        //given
        long id = 2L;
        Student found = studentRepo.findById(id).orElseThrow();
        confirmSqlWasExecutedCorrectly();
        //and
        long initialStudentCount = studentRepo.count();

        //when
        service.deleteById(id);

        //then
        assertThat(studentRepo.findAll())
                .hasSize((int) (initialStudentCount) - 1)
                .doesNotContain(found);
        fail("the contract on what to do with Lessons when deleting the Student has changed and the method is not " +
                "updated yet; this test intentionally fails for now");
    }

    @Test
    @DisplayName("create(): Saves an object as an entity in the db")
    void whenCreate_thenOK() {
        //given
        Student expectedStudent = TestStudentFactory.createStudent();
        StudentCreationDto requestData = TestStudentFactory.createCreationDto(expectedStudent);
        ResponseDto expectedResponse = TestStudentFactory.createExpectedMapping(expectedStudent);

        //when
        ResponseDto actual = service.create(requestData);

        //then
        assertCorrectDtoIsReturned(expectedResponse, actual);
        assertStudentIsSavedToDb(expectedStudent);
        assertStudentHasAMailbox();
        assertMailboxIsSavedToDb();
    }

    @Test
    @DisplayName("getById(): Throws if ID is not found")
    @Sql(value = "classpath:testSql/student-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void whenGetById_thenThrows() {
        //given
        long id = Long.MAX_VALUE;

        //when
        ThrowingCallable getById = () -> service.getById(id);

        //then
        whenMethod_thenThrows(id, getById);
    }

    @Test
    @DisplayName("deleteById(): Throws if ID is not found")
    @Sql(value = "classpath:testSql/student-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void whenDeleteById_thenThrows() {
        //given
        long id = Long.MAX_VALUE;

        //when
        ThrowingCallable deleteById = () -> service.deleteById(id);

        //then
        whenMethod_thenThrows(id, deleteById);
    }

    @Test
    @DisplayName("patchById(): Throws if ID is not found")
    @Sql(value = "classpath:testSql/student-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void whenPatchById_thenThrows() {
        //given
        long id = Long.MAX_VALUE;
        //and
        StudentUpdateDto requestData = TestStudentFactory.createUpdateDto();

        //when
        ThrowingCallable patchById = () -> service.patchById(id, requestData);

        //then
        whenMethod_thenThrows(id, patchById);
    }

    private void whenMethod_thenThrows(long id, ThrowingCallable method) {
        //given
        String expectedExceptionMsg = "Student ID %d was not found in the database.".formatted(id);

        //when
        Throwable thrown = catchThrowable(method);

        //then
        assertCorrectExceptionIsThrown(expectedExceptionMsg, thrown);
    }

    private void confirmSqlWasExecutedCorrectly() {
        List<Student> allStudents = studentRepo.findAll();
        assertThat(allStudents).hasSize(4);

        Student withId2 = allStudents.stream()
                .filter(s -> s.getId() == 2)
                .findFirst()
                .orElseThrow();
        assertThat(withId2.getEmail()).isEqualTo("kasia@kowalska.pl");
        assertThat(withId2.getAccountBalance().toPlainString()).isEqualTo("-0.01");
    }

    private void assertAllDtosAreCorrect(List<ResponseDto> expectedMappings, List<ResponseDto> actual) {
        assertThat(actual)
                .hasSize(expectedMappings.size())
                .containsAll(expectedMappings);
    }

    private void assertCorrectExceptionIsThrown(String expectedExceptionMsg, Throwable thrown) {
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedExceptionMsg);
    }

    private void assertCorrectDtoIsReturned(ResponseDto expectedResponse, ResponseDto actual) {
        assertThat(actual).isEqualTo(expectedResponse);
    }

    private void assertStudentIsSavedToDb(Student expectedStudent) {
        List<Student> allStudents = studentRepo.findAll();
        assertThat(allStudents)
                .hasSize(1)
                .contains(expectedStudent);
    }

    private void assertStudentHasAMailbox() {
        long id = 1L;
        Student student = studentRepo.findById(id).orElseThrow();
        assertThat(student.getMailbox())
                .isNotNull()
                .extracting(Mailbox::getId)
                .isEqualTo(id);
    }

    private void assertMailboxIsSavedToDb() {
        List<Mailbox> allMailboxes = mailboxRepo.findAll();
        assertThat(allMailboxes).isNotEmpty();
    }

    private void assertStudentCountDidNotChange(long initialStudentCount) {
        assertThat(studentRepo.count()).isEqualTo(initialStudentCount);
    }

    private void assertStudentWasUpdatedCorrectly(long id) {
        Student studentPostUpdate = studentRepo.findById(id).orElseThrow();
        assertThat(studentPostUpdate.getId()).isEqualTo(id);
        assertThat(studentPostUpdate.getMailbox().getId()).isEqualTo(id);
        assertThat(studentPostUpdate.getEmail()).isEqualTo("anna@wisniewska.com");
    }
}
