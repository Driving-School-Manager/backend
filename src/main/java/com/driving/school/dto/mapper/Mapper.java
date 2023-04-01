package com.driving.school.dto.mapper;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.ResponseDto;

public interface Mapper<T> {
    ResponseDto toResponseDto(T source);

    T toModel(CreationDto requestData);

    default IllegalArgumentException throwOnInvalidRequestType(CreationDto requestData) {
        String template = """
                Invalid mapper provided for request:
                %s
                
                Mapper: %s
                RequestData type: %s
                """.stripIndent();
        String msg = template.formatted(
                requestData.toString(),
                this.getClass().getSimpleName(),
                requestData.getClass().getSimpleName()
        );

        return new IllegalArgumentException(msg);
    }
}
