package com.driving.school.api;

import com.driving.school.model.StudentModel;
import com.driving.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<StudentModel> student = studentService.getStudentById(id);
        if(student.isPresent()){
            return new ResponseEntity<>(student.get(),HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
