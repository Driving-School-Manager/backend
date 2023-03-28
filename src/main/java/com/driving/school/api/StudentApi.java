package com.driving.school.api;

import com.driving.school.model.StudentModel;
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
    public ResponseEntity<List<StudentModel>> getStudents(){
        List<StudentModel> studentList = studentService.getStudents();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentModel> getStudentById(@PathVariable long id){
            return new ResponseEntity<>(studentService.getStudentById(id),HttpStatus.OK);

    }


}
