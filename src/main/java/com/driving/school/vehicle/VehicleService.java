package com.driving.school.vehicle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleDtoMapper vehicleDtoMapper;

    public List<VehicleDto> getAllVehicles(){
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleDtoMapper)
                .collect(Collectors.toList());
    }

    public VehicleDto getVehicleDtoById(Long id){
        return vehicleRepository.findById(id)
                .map(vehicleDtoMapper)
                .orElseThrow(() -> new NoVehicleFoundException("There is no such vehicle in database!"));
    }

    public Vehicle createVehicle(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }

    public VehicleDto getupdatedVehicle(Long id, Vehicle vehicle){
        return updateVehicle(id, vehicle);
    }

    public void deleteVehicleById(Long id){
        existsById(id);
        vehicleRepository.deleteById(id);
    }

    private VehicleDto updateVehicle(Long id, Vehicle vehicle){
        VehicleDto updatedVehicle = getVehicleDtoById(id);
        updatedVehicle.setBrand(vehicle.getBrand());
        updatedVehicle.setStatus(vehicle.getStatus());
        updatedVehicle.setTransmissionType(vehicle.getTransmissionType());
        updatedVehicle.setYearOfFabrication(vehicle.getYearOfFabrication());
        return updatedVehicle;

    }

    private Vehicle getVehicleById(Long id){
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new NoVehicleFoundException("No such vehicle in database!"));
    }

    private void existsById(Long id){
        if(!vehicleRepository.existsById(id))
            throw new NoVehicleFoundException("No such vehicle in database!");
    }

}
