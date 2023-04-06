package com.driving.school.service.stub;

import com.driving.school.dto.ResponseDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StubResponseDto implements ResponseDto {
    private final String someField;
    @Override
    public long id() {
        return someField.length();
    }
}
