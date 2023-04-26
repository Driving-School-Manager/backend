package com.driving.school.service.stub;

import com.driving.school.dto.ResponseDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StubResponseDto implements ResponseDto {
    private final String someStubField;

    /**
     * Returns the length of the internal field as its ID.
     */
    @Override
    public long id() {
        return someStubField.length();
    }
}
