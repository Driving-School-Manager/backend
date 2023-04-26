package com.driving.school.service;

import com.driving.school.dto.mapper.InstructorMapper;
import com.driving.school.model.Instructor;
import com.driving.school.repository.InstructorRepository;
import com.driving.school.service.util.InstructorRemovalUtil;
import org.springframework.stereotype.Service;

@Service
public class InstructorService extends CrudService<Instructor>{

    public InstructorService(
            InstructorRepository repo,
            InstructorMapper mapper,
            InstructorRemovalUtil removalUtil
    ) {
        super(repo, mapper, removalUtil);
    }

}
