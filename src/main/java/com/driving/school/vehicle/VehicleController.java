package com.driving.school.vehicle;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<VehicleDto>> getListOfVehicles(){
        List<VehicleDto> vehicles = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle){
        vehicleService.createVehicle(vehicle);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteVehicle(@RequestParam("id") Long id){
        vehicleService.deleteVehicleById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
