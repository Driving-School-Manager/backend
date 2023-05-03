package com.driving.school.service;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.ResponseDto;
import com.driving.school.dto.UpdateDto;
import com.driving.school.dto.mapper.GenericMapper;
import com.driving.school.exception.ResourceNotFoundException;
import com.driving.school.service.stub.StubCrudService;
import com.driving.school.service.stub.StubEntity;
import com.driving.school.service.stub.StubResponseDto;
import com.driving.school.service.util.RemovalUtil;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.driving.school.test_util.TestStubFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrudServiceTest {

    @Mock
    private JpaRepository<StubEntity, Long> repo;
    @Mock
    private GenericMapper<StubEntity> mapper;
    @Mock
    private RemovalUtil<StubEntity> removalUtil;
    @InjectMocks
    private StubCrudService service;

    @Test
    @DisplayName("getAll(): Gets a list of entities")
    void whenGetAll_thenOK() {
        //given
        List<StubEntity> dbContent = createDbContent();
        List<StubResponseDto> responseMappings = createExpectedResponseMappings(dbContent);
        //and
        setUpMocks_getAll(dbContent, responseMappings);

        //when
        List<ResponseDto> actual = service.getAll();

        //then
        verify(repo).findAll();
        verify(mapper, times(responseMappings.size())).toResponseDto(any());
        assertAllDtosAreCorrect(responseMappings, actual);
    }

    @Test
    @DisplayName("getAll(): On an empty DB, returns empty list")
    void whenGetAll_thenEmptyList() {
        //given
        setUpMock_repo_findAll(new ArrayList<>());

        //when
        List<ResponseDto> actual = service.getAll();

        //then
        verify(repo).findAll();
        verify(mapper, never()).toResponseDto(any());
        assertNoDtoWasReturned(actual);
    }

    @Test
    @DisplayName("getById(): Successfully gets an entity")
    void whenGetById_thenOK() {
        //given
        StubEntity expectedEntity = new StubEntity("expected entity");
        StubResponseDto expectedResponseMapping = createExpectedResponseMapping(expectedEntity);
        //and
        setUpMocks_getById(expectedEntity, expectedResponseMapping);

        //when
        ResponseDto actual = service.getById(1L);

        //then
        verify(repo).findById(anyLong());
        assertCorrectDtoIsReturned(expectedResponseMapping, actual);
    }

    @Test
    @DisplayName("patchById(): Updates an existing entity")
    void whenPatchById_thenOK() {
        //given
        StubEntity existingEntity = new StubEntity("expected entity");
        String patchedField = "patched entity";
        StubEntity entityAfterPatching = new StubEntity(patchedField);
        StubEntity savedEntity = new StubEntity("saved entity");
        StubResponseDto expectedResponseMapping = createExpectedResponseMapping(savedEntity);
        UpdateDto requestData = new UpdateDto() {}; //anonymous empty implementation (notice the {})
        //and
        setUpMocks_patchById(existingEntity, entityAfterPatching, savedEntity, expectedResponseMapping);

        //when
        ResponseDto actual = service.patchById(1L, requestData);

        //then
        verifySavedEntityWasModified(patchedField);
        //and
        assertCorrectDtoIsReturned(expectedResponseMapping, actual);
    }

    @Test
    @DisplayName("create(): Saves an object as an entity in the db")
    void whenCreate_thenOK() {
        //given
        StubEntity entityFromRequest = new StubEntity("entity from request");
        StubEntity savedEntity = new StubEntity("saved entity");
        StubResponseDto expectedResponseMapping = createExpectedResponseMapping(savedEntity);
        CreationDto requestData = new CreationDto() {}; //anonymous empty implementation (notice the {})
        //and
        setUpMocks_create(entityFromRequest, savedEntity, expectedResponseMapping);

        //when
        ResponseDto actual = service.create(requestData);

        //then
        verify(repo).save(any());
        assertCorrectDtoIsReturned(expectedResponseMapping, actual);
    }

    @Test
    @DisplayName("deleteById(): Deletes an existing entity")
    void whenDeleteById_thenOK() {
        //given
        StubEntity existingEntity = new StubEntity("expected entity");
        //and
        setUpMock_repo_findById(existingEntity);

        //when
        service.deleteById(1L);

        //then
        verify(repo).findById(anyLong());
        verify(removalUtil).deleteEntity(existingEntity);
    }

    @Test
    @DisplayName("getById(): Throws if ID is not found")
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
    @DisplayName("deleteById(): Throws if ID is not found")
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
    @DisplayName("patchById(): Throws if ID is not found")
    void whenPatchById_thenThrows() {
        //given
        UpdateDto requestData = new UpdateDto() {}; //anonymous empty implementation (notice the {})

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
        setUpMock_repo_findById(null);

        //when
        Throwable thrown = catchThrowable(method);

        //then
        invocationVerifier.run();

        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("String ID 1 was not found in the database.");
    }

    private void setUpMock_mapper_updateFields(StubEntity patchedEntity) {
        Answer<Void> answer = call -> {
            StubEntity entityToPatch = call.getArgument(0, StubEntity.class);
            String patchText = patchedEntity.getSomeField();
            entityToPatch.setSomeField(patchText);
            return null;
        };
        willAnswer(answer).given(mapper).updateFields(any(), any());
    }

    private void setUpMocks_getAll(List<StubEntity> dbContent, List<StubResponseDto> responseMappings) {
        setUpMock_repo_findAll(dbContent);
        setUpMock_mapper_toResponseDto(responseMappings);
    }

    private void setUpMocks_getById(StubEntity expectedEntity, StubResponseDto expectedResponseMapping) {
        setUpMock_repo_findById(expectedEntity);
        setUpMock_mapper_toResponseDto(expectedResponseMapping);
    }

    private void setUpMocks_patchById(StubEntity existingEntity,
                                      StubEntity patchedEntity,
                                      StubEntity savedEntity,
                                      StubResponseDto expectedResponseMapping) {
        setUpMock_repo_findById(existingEntity);
        setUpMock_mapper_updateFields(patchedEntity);
        setUpMock_repo_save(patchedEntity, savedEntity);
        setUpMock_mapper_toResponseDto(expectedResponseMapping);
    }

    private void setUpMocks_create(StubEntity entityFromRequest,
                                   StubEntity savedEntity,
                                   StubResponseDto expectedResponseMapping) {
        setUpMock_mapper_toModel(entityFromRequest);
        setUpMock_repo_save(entityFromRequest, savedEntity);
        setUpMock_mapper_toResponseDto(expectedResponseMapping);
    }

    private void setUpMock_repo_findAll(List<StubEntity> expectedDbContent) {
        given(repo.findAll()).willReturn(expectedDbContent);
    }

    private void setUpMock_repo_findById(StubEntity fakeEntity) {
        given(repo.findById(anyLong())).willReturn(Optional.ofNullable(fakeEntity));
    }

    private void setUpMock_repo_save(StubEntity entityToSave, StubEntity entityAfterSaving) {
        given(repo.save(entityToSave)).willReturn(entityAfterSaving);
    }

    private void setUpMock_mapper_toModel(StubEntity fakeEntity) {
        given(mapper.toModel(any())).willReturn(fakeEntity);
    }

    private void setUpMock_mapper_toResponseDto(StubResponseDto responseMapping) {
        given(mapper.toResponseDto(any())).willReturn(responseMapping);
    }

    private void setUpMock_mapper_toResponseDto(List<StubResponseDto> responseMappings) {
        if (responseMappings.size() > 1) {
            setUpMapperForMultipleReturns(responseMappings);
        } else if (responseMappings.size() == 1) {
            setUpMock_mapper_toResponseDto(responseMappings.get(0));
        }
        //nothing if size() == 0
    }

    private void setUpMapperForMultipleReturns(List<StubResponseDto> responseMappings) {
        StubResponseDto[] responsesAsArray = responseMappings.toArray(StubResponseDto[]::new);

        given(mapper.toResponseDto(any())).willReturn(
                responsesAsArray[0],
                Arrays.copyOfRange(responsesAsArray, 1, responsesAsArray.length)
        );
    }

    private void assertAllDtosAreCorrect(List<StubResponseDto> expectedResponses,
                                         List<ResponseDto> actualResponses) {
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
        assertThat(actualResponses)
                .isNotNull()
                .isEmpty();
    }

    private void assertCorrectDtoIsReturned(StubResponseDto expectedResponse,
                                            ResponseDto actualResponse
    ) {
        ArgumentCaptor<StubEntity> mapperArgument = ArgumentCaptor.forClass(StubEntity.class);
        //then
        verify(mapper).toResponseDto(mapperArgument.capture());
        assertThat(mapperArgument.getValue()).isEqualTo(expectedResponse.stubEntity());
        //and
        assertThat(actualResponse)
                .isNotNull()
                .extracting(ResponseDto::id)
                .isEqualTo(expectedResponse.id());
    }

    private void verifySavedEntityWasModified(String expectedModifiedField) {
        InOrder inOrder = inOrder(mapper, repo);

        //1. updateFields
        ArgumentCaptor<StubEntity> updateArgCaptor = ArgumentCaptor.forClass(StubEntity.class);
        inOrder.verify(mapper).updateFields(updateArgCaptor.capture(), any());

        //2. save
        ArgumentCaptor<StubEntity> saveArgCaptor = ArgumentCaptor.forClass(StubEntity.class);
        inOrder.verify(repo).save(saveArgCaptor.capture());

        assertThat(updateArgCaptor.getValue()).isEqualTo(saveArgCaptor.getValue());
        assertThat(saveArgCaptor.getValue().getSomeField()).isEqualTo(expectedModifiedField);
    }

}