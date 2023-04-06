package com.driving.school.dto.mapper;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.ResponseDto;
import com.driving.school.dto.UpdateDto;

public interface GenericMapper<T> {
    ResponseDto toResponseDto(T source);

    T toModel(CreationDto requestData);

    void updateFields(T destination, UpdateDto requestData);

    default void ifNotInstanceThrow(Object requestData, Class<?> expectedDtoType) {
        if (!expectedDtoType.isInstance(requestData)) {
            throw getInvalidDtoTypeException(requestData);
        }
    }

    private IllegalArgumentException getInvalidDtoTypeException(Object requestData) {
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
