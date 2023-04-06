package com.driving.school.service;

import com.driving.school.dto.mapper.GenericMapper;
import com.driving.school.service.util.RemovalUtil;
import org.springframework.data.jpa.repository.JpaRepository;

public class StubCrudService extends CrudService<String> {

    public StubCrudService(
            JpaRepository<String, Long> repo,
            GenericMapper<String> mapper,
            RemovalUtil<String> removalUtil
    ) {
        super(repo, mapper, removalUtil);
    }
}
