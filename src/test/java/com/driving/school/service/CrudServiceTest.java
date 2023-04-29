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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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
@DisplayName("getAll(): gets a list of entities")
    void whenGetAll_thenOK() {
        //given
        List<String> dbContent = createDbContent();
        List<StubResponseDto> responseMappings = setUpMock_mapper_toResponseDto(dbContent);
        //and
        setUpMock_repo_findAll(dbContent);

        //when
        List<ResponseDto> actual = service.getAll();

        //then
        verify(repo).findAll();
        assertAllDtosAreCorrect(responseMappings, actual);
    }

    @Test
    @DisplayName("getAll(): on an empty DB, returns empty list")
    void whenGetAll_thenEmptyList() {
        //given
        setUpMock_repo_findAll(new ArrayList<>());

        //when
        List<ResponseDto> actual = service.getAll();

        //then
        verify(repo).findAll();
        assertNoDtoWasReturned(actual);
    }

    @Test
    @DisplayName("getById(): gets an entity")
    void whenGetById_thenOK() {
        //given
        String foundEntity = setUpMock_repo_findById();
        //and
        StubResponseDto expectedResponseMapping = setUpMock_mapper_toResponseDto(foundEntity);

        //when
        ResponseDto actual = service.getById(1L);

        //then
        verify(repo).findById(anyLong());
        assertCorrectDtoIsReturned(expectedResponseMapping, actual);
    }

    @Test
    @DisplayName("patchById(): updates an existing entity")
    void whenPatchById_thenOK() {
        //given
        setUpMock_repo_findById();
        //and
        String patchedEntity = setUpMock_repo_save();
        //and
        StubResponseDto expectedResponseMapping = setUpMock_mapper_toResponseDto(patchedEntity);
        //and
        //anonymous empty implementation (notice the {})
        UpdateDto requestData = new UpdateDto() {};

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
        setUpMock_mapper_toModel();
        //and
        String savedEntity = setUpMock_repo_save();
        //and
        StubResponseDto expectedResponseMapping = setUpMock_mapper_toResponseDto(savedEntity);
        //and
        //anonymous empty implementation (notice the {})
        CreationDto requestData = new CreationDto() {};

        //when
        ResponseDto actual = service.create(requestData);

        //then
        verify(repo).save(any());
        assertCorrectDtoIsReturned(expectedResponseMapping, actual);
    }

    @Test
    @DisplayName("deleteById(): deletes existing entity")
    void whenDeleteById_thenOK() {
        //given
        String savedEntity = setUpMock_repo_findById();

        //when
        service.deleteById(1L);

        //then
        verify(repo).findById(anyLong());
        verify(removalUtil).deleteEntity(savedEntity);
    }

    @Test
    @DisplayName("getById(): throws if ID doesn't exist")
    void whenGetById_thenThrows() {
        //when & then
        ThrowingCallable getById = () -> service.getById(1L);
        Runnable invocationVerifier = () -> {
            verify(repo).findById(1L);
            verify(mapper, never()).toResponseDto(any());
        };

        whenMethod_thenThrows(getById, invocationVerifier);
    }

    @Test
    @DisplayName("deleteById(): throws if ID doesn't exist")
    void whenDeleteById_thenThrows() {
        //when & then
        ThrowingCallable deleteById = () -> service.deleteById(1L);
        Runnable invocationVerifier = () -> {
            verify(repo).findById(1L);
            verify(removalUtil, never()).deleteEntity(any());
        };

        whenMethod_thenThrows(deleteById, invocationVerifier);
    }

    @Test
    @DisplayName("patchById(): throws if ID doesn't exist")
    void whenPatchById_thenThrows() {
        //given
        //anonymous empty implementation (notice the {})
        UpdateDto requestData = new UpdateDto() {};

        //when & then
        ThrowingCallable patchById = () -> service.patchById(1L, requestData);
        Runnable invocationVerifier = () -> {
            verify(repo).findById(1L);
            verify(repo, never()).save(any());
            verify(mapper, never()).updateFields(any(), any());
        };

        whenMethod_thenThrows(patchById, invocationVerifier);
    }

    private void whenMethod_thenThrows(ThrowingCallable method, Runnable invocationVerifier) {
        //given
        setUpMock_repo_findById_whileDbIsEmpty();

        //when
        Throwable thrown = catchThrowable(method);

        //then
        invocationVerifier.run();

        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("String ID 1 was not found in the database.");
    }

    private void setUpMock_repo_findAll(List<String> expectedDbContent) {
        given(repo.findAll())
                .willReturn(expectedDbContent);
    }

    private String setUpMock_repo_findById() {
        return setUpMock_repo(entity ->
                given(repo.findById(anyLong()))
                        .willReturn(Optional.of(entity))
        );
    }

    private String setUpMock_repo_save() {
        return setUpMock_repo(entity ->
                given(repo.save(any()))
                        .willReturn(entity)
        );
    }

    private String setUpMock_repo(Consumer<String> given) {
        String expectedEntity = "expected entity";
        given.accept(expectedEntity);
        return expectedEntity;
    }

    private void setUpMock_repo_findById_whileDbIsEmpty() {
        given(repo.findById(anyLong()))
                .willReturn(Optional.empty());
    }

    private void setUpMock_mapper_toModel() {
        given(mapper.toModel(any()))
                .willReturn("new entity");
    }

    private StubResponseDto setUpMock_mapper_toResponseDto(String fakeSavedEntity) {
        return setUpMock_mapper_toResponseDto(List.of(fakeSavedEntity)).get(0);
    }

    private List<StubResponseDto> setUpMock_mapper_toResponseDto(List<String> fakeSavedEntities) {
        List<StubResponseDto> expectedResponseMappings = createExpectedResponseMappings(fakeSavedEntities);

        setUpMapperReturnValues(expectedResponseMappings);

        return expectedResponseMappings;
    }

    private List<StubResponseDto> createExpectedResponseMappings(List<String> fakeSavedEntities) {
        return fakeSavedEntities.stream()
                .map(StubResponseDto::new)
                .toList();
    }

    private void setUpMapperReturnValues(List<StubResponseDto> responseMappings) {
        if (responseMappings.size() > 1) {
            setUpMapperForMultipleReturns(responseMappings);
        } else if (responseMappings.size() == 1) {
            setUpMapperForSingleReturn(responseMappings.get(0));
        }
        //nothing if size() == 0
    }

    private void setUpMapperForMultipleReturns(List<StubResponseDto> responseMappings) {
        StubResponseDto[] responsesAsArray = responseMappings.toArray(StubResponseDto[]::new);

        given(mapper.toResponseDto(any()))
                .willReturn(
                        responsesAsArray[0],
                        Arrays.copyOfRange(responsesAsArray, 1, responsesAsArray.length)
                );
    }

    private void setUpMapperForSingleReturn(StubResponseDto responseMapping) {
        given(mapper.toResponseDto(any()))
                .willReturn(responseMapping);
    }

    private void assertAllDtosAreCorrect(List<StubResponseDto> expectedResponses,
                                         List<ResponseDto> actualResponses
    ) {
        verify(mapper, times(expectedResponses.size()))
                .toResponseDto(anyString());
        assertThat(actualResponses)
                .isNotNull()
                .extracting(ResponseDto::id)
                .containsExactlyElementsOf(
                        expectedResponses.stream()
                                .map(StubResponseDto::id)
                                .toList()
                );
    }

    private void assertNoDtoWasReturned(List<ResponseDto> actualResponses) {
        verify(mapper, never()).toResponseDto(any());
        assertThat(actualResponses)
                .isNotNull()
                .isEmpty();
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


    private List<String> createDbContent() {
        return List.of("a", "bc", "def", "ghij");
    }
}