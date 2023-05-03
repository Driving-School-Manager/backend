package com.driving.school.service.stub;

import com.driving.school.dto.ResponseDto;

public record StubResponseDto(StubEntity stubEntity) implements ResponseDto {
    /**
     * Returns the length of the internal field as its ID.
     */
    @Override
    public long id() {
        return stubEntity.getSomeField().length();
    }
}
