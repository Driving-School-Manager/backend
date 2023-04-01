package com.driving.school.api;

import com.driving.school.dto.StudentCreationDto;
import com.driving.school.dto.StudentResponseDto;
import com.driving.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/students")
public class StudentApi {
    private final StudentService service;

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getStudents() {
        List<StudentResponseDto> students = service.getStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable long id) {
        StudentResponseDto found = service.getStudentById(id);
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable long id) {
        service.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(
            @PathVariable long id,
            @RequestBody StudentCreationDto student
    ) {
        StudentResponseDto updated = service.updateStudent(id, student);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<StudentResponseDto> addStudent(@RequestBody StudentCreationDto student) {
        StudentResponseDto createdStudent = service.addStudent(student);
        URI newStudentLocation = getNewResourceLocation(createdStudent.id());

        return ResponseEntity
                .created(newStudentLocation)
                .body(createdStudent);
    }

    private URI getNewResourceLocation(long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
