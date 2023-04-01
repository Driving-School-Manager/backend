package com.driving.school.api;

import com.driving.school.dto.StudentCreationDto;
import com.driving.school.dto.StudentResponseDto;
import com.driving.school.service.EntityCreationApi;
import com.driving.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/students")
public class StudentApi implements EntityCreationApi {
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

    @PostMapping
    public ResponseEntity<StudentResponseDto> addStudent(@RequestBody StudentCreationDto requestData) {
        StudentResponseDto createdStudent = service.addStudent(requestData);
        URI newStudentLocation = getNewResourceLocation(createdStudent.id());

        return createResponse(createdStudent, newStudentLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable long id) {
        service.deleteStudentById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> replaceStudent(
            @PathVariable long id,
            @RequestBody StudentCreationDto requestData
    ) {
        StudentResponseDto replacement = service.replaceStudent(id, requestData);

        return ResponseEntity.ok(replacement);
    }

}
