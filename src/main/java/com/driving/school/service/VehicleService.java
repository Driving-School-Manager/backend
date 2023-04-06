package com.driving.school.service;

import com.driving.school.dto.mapper.VehicleMapper;
import com.driving.school.model.Vehicle;
import com.driving.school.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService extends CrudService<Vehicle> {
    @Autowired
    public VehicleService(VehicleRepository repo, VehicleMapper mapper) {
        super(repo, mapper);
    }

}
