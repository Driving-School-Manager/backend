package com.driving.school.dto.mapper;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.ResponseDto;

public interface Mapper<T> {
    ResponseDto toResponseDto(T source);

    T toModel(CreationDto requestData);
}
