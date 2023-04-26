package com.driving.school.service;

import com.driving.school.dto.ResponseDto;
import com.driving.school.dto.mapper.GenericMapper;
import com.driving.school.service.stub.StubCrudService;
import com.driving.school.service.stub.StubResponseDto;
import com.driving.school.service.util.RemovalUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrudServiceTest {

    @Mock
    private JpaRepository<String, Long> repo;
    @Mock
    private GenericMapper<String> mapper;
    @Mock
    private RemovalUtil<String> removalUtil;
    @InjectMocks
    private StubCrudService service;

    @Test
    @DisplayName("Can getAll entities")
    void canGetAll() {
        //given
        List<String> dbContent = createDbContent();
        List<StubResponseDto> responseMappings = createResponseMappings(dbContent);
        List<Long> expected = createExpectedIds(responseMappings);
        //and
        given(repo.findAll())
                .willReturn(dbContent);
        //and
        ArgumentCaptor<String> argCaptor = ArgumentCaptor.forClass(String.class);
        given(mapper.toResponseDto(argCaptor.capture()))
                .willReturn(
                        responseMappings.get(0),
                        responseMappings.get(1),
                        responseMappings.get(2)
                );

        //when
        List<ResponseDto> actual = service.getAll();

        //then
        verify(repo).findAll();
        verify(mapper, times(dbContent.size())).toResponseDto(anyString());

        assertThat(actual)
                .isNotNull()
                .extracting(ResponseDto::id)
                .containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Can getById when ID exists")
    void canGetById() {
        //given
        String foundInDb = "asdasd";
        StubResponseDto responseMapping = new StubResponseDto(foundInDb);
        Long expected = responseMapping.id();
        //and
        given(repo.findById(anyLong()))
                .willReturn(Optional.of(foundInDb));
        //and
        ArgumentCaptor<String> argCaptor = ArgumentCaptor.forClass(String.class);
        given(mapper.toResponseDto(argCaptor.capture()))
                .willReturn(responseMapping);

        //when
        ResponseDto actual = service.getById(1L);

        //then
        verify(repo).findById(anyLong());
        verify(mapper).toResponseDto(anyString());

        assertThat(actual)
                .isNotNull()
                .extracting(ResponseDto::id)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Can getAll on an empty DB, returning empty list")
    void canGetAllWhenEmpty() {
        //given
        given(repo.findAll())
                .willReturn(new ArrayList<>());

        //when
        List<ResponseDto> actual = service.getAll();

        //then
        verify(repo).findAll();
        verify(mapper, never()).toResponseDto(any());

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }

    private List<String> createDbContent() {
        return List.of("a", "bc", "def");
    }

    private List<StubResponseDto> createResponseMappings(List<String> dbContent) {
        return dbContent.stream()
                .map(StubResponseDto::new)
                .toList();
    }

    private List<Long> createExpectedIds(List<StubResponseDto> responseMappings) {
        return responseMappings.stream()
                .map(ResponseDto::id)
                .toList();
    }
}