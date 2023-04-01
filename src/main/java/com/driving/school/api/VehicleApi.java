package com.driving.school.api;

import com.driving.school.dto.VehicleCreationDto;
import com.driving.school.dto.VehicleResponseDto;
import com.driving.school.service.EntityCreationApi;
import com.driving.school.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/vehicles")
public class VehicleApi implements EntityCreationApi {
    private final VehicleService service;

    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> getVehicles(){
        List<VehicleResponseDto> vehicles = service.getVehicles();

        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> getVehiclesById(@PathVariable Long id){
        VehicleResponseDto found = service.getVehicleById(id);

        return ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDto> addVehicle(@RequestBody VehicleCreationDto requestData){
        VehicleResponseDto createdVehicle = service.addVehicle(requestData);
        URI newVehicleLocation = getNewResourceLocation(createdVehicle.id());

        return createResponse(createdVehicle, newVehicleLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleById(@PathVariable Long id){
        service.deleteVehicleById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> replaceVehicle(
            @PathVariable Long id,
            @RequestBody VehicleCreationDto requestData
    ) {
        VehicleResponseDto replacement = service.replaceVehicle(id, requestData);

        return ResponseEntity.ok(replacement);
    }

}
