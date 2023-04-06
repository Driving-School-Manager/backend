package com.driving.school.api;

import com.driving.school.dto.InstructorRequestDto;
import com.driving.school.service.InstructorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/instructors")
public class InstructorApi extends CrudApi<InstructorRequestDto, InstructorRequestDto> {
    public InstructorApi(InstructorService service) {
        super(service);
    }
}
