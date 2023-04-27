package com.driving.school.service;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.ResponseDto;
import com.driving.school.dto.UpdateDto;
import com.driving.school.dto.mapper.GenericMapper;
import com.driving.school.exception.ResourceNotFoundException;
import com.driving.school.service.stub.StubCrudService;
import com.driving.school.service.stub.StubResponseDto;
import com.driving.school.service.util.RemovalUtil;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
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
import static org.assertj.core.api.Assertions.catchThrowable;
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
    @DisplayName("getAll(): gets a list entities")
    void whenGetAll_thenOK() {
        //given
        List<String> dbContent = createDbContent();
        List<StubResponseDto> responseMappings = createResponseMappings(dbContent);
        List<Long> expected = createExpectedIds(responseMappings);
        //and
        given(repo.findAll())
                .willReturn(dbContent);
        //and
        given(mapper.toResponseDto(any()))
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
    @DisplayName("getAll(): on an empty DB, returns empty list")
    void whenGetAll_thenEmptyList() {
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

    @Test
    @DisplayName("getById(): gets an entity")
    void whenGetById_thenOK() {
        //given
        String foundInDb = "existing db entity";
        given(repo.findById(anyLong()))
                .willReturn(Optional.of(foundInDb));
        //and
        StubResponseDto expectedResponseMapping = new StubResponseDto(foundInDb);
        given(mapper.toResponseDto(any()))
                .willReturn(expectedResponseMapping);

        //when
        ResponseDto actual = service.getById(1L);

        //then
        verify(repo).findById(anyLong());
        assertCorrectDtoIsReturned(expectedResponseMapping, actual);
    }

    @Test
    @DisplayName("deleteById(): deletes existing entity")
    void whenDeleteById_thenOK() {
        //given
        String savedEntity = "saved entity";
        given(repo.findById(anyLong()))
                .willReturn(Optional.of(savedEntity));

        //when
        service.deleteById(1L);

        //then
        verify(repo).findById(anyLong());
        verify(removalUtil).deleteEntity(savedEntity);
    }

    @Test
    @DisplayName("patchById(): updates an existing entity")
    void whenPatchById_thenOK() {
        //given
        //anonymous empty implementation (notice the {})
        UpdateDto requestData = new UpdateDto() {};
        //and
        String savedEntity = "saved entity";
        given(repo.findById(anyLong()))
                .willReturn(Optional.of(savedEntity));
        //and
        String patchedEntity = "patched entity";
        given(repo.save(any()))
                .willReturn(patchedEntity);
        //and
        StubResponseDto expectedResponseMapping = new StubResponseDto(patchedEntity);
        given(mapper.toResponseDto(any()))
                .willReturn(expectedResponseMapping);

        //when
        ResponseDto actual = service.patchById(1L, requestData);

        //then
        verify(repo).findById(anyLong());
        assertCorrectDtoIsReturned(expectedResponseMapping, actual);
    }


    @Test
    @DisplayName("create(): creates and saves an entity")
    void whenCreate_thenOK() {
        //given
        given(mapper.toModel(any()))
                .willReturn("new entity");
        //and
        String savedEntity = "saved entity";
        given(repo.save(any()))
                .willReturn(savedEntity);
        //and
        StubResponseDto expectedResponseMapping = new StubResponseDto(savedEntity);
        given(mapper.toResponseDto(any()))
                .willReturn(expectedResponseMapping);
        //and
        //anonymous empty implementation (notice the {})
        CreationDto requestData = new CreationDto() {};

        //when
        ResponseDto actual = service.create(requestData);

        //then
        verify(repo).save(any());
        assertCorrectDtoIsReturned(expectedResponseMapping, actual);
    }

    private void assertCorrectDtoIsReturned(StubResponseDto expectedResponse,
                                            ResponseDto actualResponse
    ) {
        ArgumentCaptor<String> mapperArgument = ArgumentCaptor.forClass(String.class);
        //then
        verify(mapper).toResponseDto(mapperArgument.capture());
        assertThat(mapperArgument.getValue())
                .isEqualTo(expectedResponse.getStubEntity());
        //and
        assertThat(actualResponse)
                .isNotNull()
                .extracting(ResponseDto::id)
                .isEqualTo(expectedResponse.id());
    }

    @Test
    @DisplayName("getById(): throws if ID doesn't exist")
    void whenGetById_thenThrows() {
        //when & then
        ThrowingCallable method = () -> service.getById(1L);
        Runnable invocationVerifier = () -> {
            verify(repo).findById(1L);
            verify(mapper, never()).toResponseDto(any());
        };

        whenMethod_thenThrows(method, invocationVerifier);
    }

    @Test
    @DisplayName("deleteById(): throws if ID doesn't exist")
    void whenDeleteById_thenThrows() {
        //when & then
        ThrowingCallable method = () -> service.deleteById(1L);
        Runnable invocationVerifier = () -> {
            verify(repo).findById(1L);
            verify(removalUtil, never()).deleteEntity(any());
        };

        whenMethod_thenThrows(method, invocationVerifier);
    }

    @Test
    @DisplayName("patchById(): throws if ID doesn't exist")
    void whenPatchById_thenThrows() {
        //given
        //anonymous empty implementation (notice the {})
        UpdateDto requestData = new UpdateDto() {};

        //when
        ThrowingCallable method = () -> service.patchById(1L, requestData);
        Runnable invocationVerifier = () -> {
            verify(repo).findById(1L);
            verify(repo, never()).save(any());
            verify(mapper, never()).updateFields(any(), any());
        };

        whenMethod_thenThrows(method, invocationVerifier);
    }

    private void whenMethod_thenThrows(ThrowingCallable method, Runnable invocationVerifier) {
        //given
        given(repo.findById(anyLong()))
                .willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(method);

        //then
        invocationVerifier.run();

        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("String ID 1 was not found in the database.");
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