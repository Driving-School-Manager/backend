package com.driving.school.api;

import com.driving.school.dto.StudentDetailsDTO;
import com.driving.school.dto.StudentListItemDTO;
import com.driving.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/students")
public class StudentApi {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentListItemDTO>> getStudents() {
        List<StudentListItemDTO> studentList = studentService.getStudents();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDetailsDTO> getStudentById(@PathVariable long id) {
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudentById(@PathVariable long id) {
        studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStudent(
            @PathVariable long id,
            @RequestBody StudentDetailsDTO student
    ) {
        studentService.updateStudent(id, student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addStudent(@RequestBody StudentDetailsDTO student) {
        studentService.addStudent(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
