package com.driving.school.service;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.ResponseDto;
import com.driving.school.dto.mapper.Mapper;
import com.driving.school.exception.ResourceNotFoundException;
import com.driving.school.model.BaseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public abstract class CrudService<T extends BaseModel> {
    private final JpaRepository<T, Long> repo;
    private final Mapper<T> mapper;

    public List<ResponseDto> getAll() {
        return repo.findAll().stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public ResponseDto getById(long id) {
        return repo.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> createException(id));
    }

    public ResponseDto create(CreationDto requestData) {
        T newModel = mapper.toModel(requestData);
        // we should use the return value for further processing
        // it's not the same as newModel (it has an ID, for example)
        T created = repo.save(newModel);

        return mapper.toResponseDto(created);
    }

    public void deleteById(long id) {
        throwIfNotInDatabase(id);
        repo.deleteById(id);
    }

    public ResponseDto replace(long id, CreationDto requestData) {
        deleteById(id);

        return create(requestData);
    }

    protected abstract String getNotFoundExceptionTemplate();

    private void throwIfNotInDatabase(Long id) {
        if (!repo.existsById(id)) {
            throw createException(id);
        }
    }

    private ResourceNotFoundException createException(Long id) {
        String msg = getNotFoundExceptionTemplate().formatted(id);
        return new ResourceNotFoundException(msg);
    }
}
