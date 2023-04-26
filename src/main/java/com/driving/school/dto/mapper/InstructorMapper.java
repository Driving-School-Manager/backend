package com.driving.school.dto.mapper;

import com.driving.school.dto.*;
import com.driving.school.model.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class InstructorMapper implements GenericMapper<Instructor> {
    @Override
    public abstract InstructorResponseDto toResponseDto(Instructor entity);

    @Override
    public Instructor toModel(CreationDto requestData) {
        ifNotInstanceThrow(requestData, InstructorRequestDto.class);
        InstructorRequestDto source = (InstructorRequestDto) requestData;

        return createEntity(source);

    }

    @Override
    public void updateFields(Instructor destination, UpdateDto requestData) {
        ifNotInstanceThrow(requestData, InstructorRequestDto.class);
        InstructorRequestDto source = (InstructorRequestDto) requestData;

        patchEntity(destination, source);
    }

    @Mapping(target = "id", constant = "0L")
    protected abstract Instructor createEntity(InstructorRequestDto source);

    protected abstract void patchEntity(@MappingTarget Instructor destination, InstructorRequestDto source);

}
