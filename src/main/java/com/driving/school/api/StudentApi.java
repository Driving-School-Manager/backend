package com.driving.school.api;

import com.driving.school.dto.StudentCreationDto;
import com.driving.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/students")
public class StudentApi extends CrudApi<StudentCreationDto> {

    @Autowired
    public StudentApi(StudentService service) {
        super(service);
    }
}
