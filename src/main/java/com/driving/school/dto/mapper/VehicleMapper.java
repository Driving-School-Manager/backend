package com.driving.school.dto.mapper;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.UpdateDto;
import com.driving.school.dto.VehicleRequestDto;
import com.driving.school.dto.VehicleResponseDto;
import com.driving.school.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class VehicleMapper implements GenericMapper<Vehicle> {

    @Override
    public abstract VehicleResponseDto toResponseDto(Vehicle entity);

    @Override
    public Vehicle toModel(CreationDto requestData) {
        ifNotInstanceThrow(requestData, VehicleRequestDto.class);
        VehicleRequestDto source = (VehicleRequestDto) requestData;

        return createEntity(source);
    }

    @Override
    public void updateFields(Vehicle destination, UpdateDto requestData) {
        ifNotInstanceThrow(requestData, VehicleRequestDto.class);
        VehicleRequestDto source = (VehicleRequestDto) requestData;

        patchEntity(destination, source);
    }

    @Mapping(target = "id", constant = "0L")
    protected abstract Vehicle createEntity(VehicleRequestDto source);

    protected abstract void patchEntity(@MappingTarget Vehicle destination, VehicleRequestDto source);


}
