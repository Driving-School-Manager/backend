package com.driving.school.test_util;

import com.driving.school.service.stub.StubEntity;
import com.driving.school.service.stub.StubResponseDto;

import java.util.List;
import java.util.stream.Stream;

public class TestDataFactory {
    public static List<StubEntity> createDbContent() {
        return Stream.of("a", "bc", "def", "ghij")
                .map(StubEntity::new)
                .toList();
    }

    public static List<StubResponseDto> createExpectedResponseMappings(List<StubEntity> fakeEntities) {
        return fakeEntities.stream()
                .map(StubResponseDto::new)
                .toList();
    }

    public static StubResponseDto createExpectedResponseMapping(StubEntity fakeEntity) {
        return new StubResponseDto(fakeEntity);
    }
}
