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

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

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
        List<String> dbContent = List.of("a", "bc", "def");
        List<StubResponseDto> responseMappings = dbContent.stream()
                .map(StubResponseDto::new)
                .toList();
        List<Long> expected = responseMappings.stream()
                .map(ResponseDto::id)
                .toList();
        ArgumentCaptor<String> argCaptor = ArgumentCaptor.forClass(String.class);
        given(repo.findAll()).willReturn(dbContent);
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
                .extracting(ResponseDto::id)
                .containsExactlyElementsOf(expected);
    }

}