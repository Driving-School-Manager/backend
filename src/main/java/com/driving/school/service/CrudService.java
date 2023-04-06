package com.driving.school.service;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.ResponseDto;
import com.driving.school.dto.UpdateDto;
import com.driving.school.dto.mapper.GenericMapper;
import com.driving.school.exception.ResourceNotFoundException;
import com.driving.school.service.util.RemovalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public abstract class CrudService<T> {
    protected final JpaRepository<T, Long> repo;
    protected final GenericMapper<T> mapper;
    protected final RemovalUtil<T> removalUtil;

    public List<ResponseDto> getAll() {
        return repo.findAll().stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public ResponseDto getById(long id) {
        return repo.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> buildException(id));
    }

    public ResponseDto create(CreationDto requestData) {
        T newModel = mapper.toModel(requestData);
        // we should use the return value for further processing
        // it's not the same as newModel (it has an ID, for example)
        T created = repo.save(newModel);

        return mapper.toResponseDto(created);
    }

    @Transactional
    public void deleteById(long id) {
        T entityToDelete = retrieveFromRepoOrThrow(id);

        removalUtil.deleteEntity(entityToDelete);
    }

    // TODO are we going to support PUT? otherwise this is unnecessary
//    @Transactional
//    public ResponseDto replace(long id, CreationDto requestData) {
//        deleteById(id);
//
//        return create(requestData);
//    }

    @Transactional
    public ResponseDto patchById(long id, UpdateDto requestData) {
        T entityToPatch = retrieveFromRepoOrThrow(id);

        mapper.updateFields(entityToPatch, requestData);

        T patched = repo.save(entityToPatch);

        return mapper.toResponseDto(patched);
    }

    protected T retrieveFromRepoOrThrow(long id) {
        return repo.findById(id)
                .orElseThrow(() -> buildException(id));
    }

    protected ResourceNotFoundException buildException(long id) {
        String msg = getNotFoundExceptionTemplate().formatted(id);
        return new ResourceNotFoundException(msg);
    }

    protected String getNotFoundExceptionTemplate() {
        return this.getClass().getGenericSuperclass().toString()
                .replaceAll(".*\\.", "")
                .replace(">", "")
                .concat(" ID %d was not found in the database.");
    }
}
