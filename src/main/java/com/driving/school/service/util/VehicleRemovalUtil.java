package com.driving.school.service.util;

import com.driving.school.model.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleRemovalUtil implements RemovalUtil<Vehicle> {
    // TODO this is actually a pretty complex operation
    // TODO we need to decide what to do with Lessons etc. before we attempt to implement it
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteEntity(Vehicle vehicle) {
//        long vehicleId = vehicle.getId();
//        log.info("Deleting Vehicle with ID {}...", vehicleId);

        throw new UnsupportedOperationException("Deleting Vehicles is broken right now");
    }
}
