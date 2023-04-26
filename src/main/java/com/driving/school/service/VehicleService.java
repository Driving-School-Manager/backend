package com.driving.school.service;

import com.driving.school.dto.mapper.VehicleMapper;
import com.driving.school.model.Vehicle;
import com.driving.school.repository.VehicleRepository;
import com.driving.school.service.util.VehicleRemovalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService extends CrudService<Vehicle> {
    @Autowired
    public VehicleService(
            VehicleRepository repo,
            VehicleMapper mapper,
            VehicleRemovalUtil removalUtil
    ) {
        super(repo, mapper, removalUtil);
    }

}
