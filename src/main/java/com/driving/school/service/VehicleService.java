package com.driving.school.service;

import com.driving.school.dto.VehicleCreationDto;
import com.driving.school.dto.VehicleResponseDto;
import com.driving.school.exception.VehicleNotFoundException;
import com.driving.school.model.Vehicle;
import com.driving.school.dto.mapper.VehicleMapper;
import com.driving.school.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository repo;
    private final VehicleMapper mapper;

    public List<VehicleResponseDto> getVehicles() {
        return repo.findAll().stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public VehicleResponseDto getVehicleById(Long id) {
        return repo.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(VehicleNotFoundException.supplyFrom(id));
    }

    public VehicleResponseDto addVehicle(VehicleCreationDto requestData) {
        Vehicle newVehicleModel = mapper.toModel(requestData);

        Vehicle created = repo.save(newVehicleModel);

        return mapper.toResponseDto(created);
    }

    public void deleteVehicleById(Long id) {
        throwIfNotInDatabase(id);
        repo.deleteById(id);
    }

    public VehicleResponseDto replaceVehicle(Long id, VehicleCreationDto requestData) {
        deleteVehicleById(id);

        return addVehicle(requestData);

    }

    private void throwIfNotInDatabase(Long id) {
        if (!repo.existsById(id)) {
            throw VehicleNotFoundException.supplyFrom(id).get();
        }
    }

}
