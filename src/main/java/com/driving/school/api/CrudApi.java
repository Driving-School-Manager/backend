package com.driving.school.api;

import com.driving.school.dto.CreationDto;
import com.driving.school.dto.ResponseDto;
import com.driving.school.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
public abstract class CrudApi<T extends CreationDto> {

    private final CrudService<?> service;

    @GetMapping
    public ResponseEntity<List<ResponseDto>> getAll() {
        List<ResponseDto> allEntitiesDto = service.getAll();

        return ResponseEntity.ok(allEntitiesDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable long id) {
        ResponseDto foundDto = service.getById(id);

        return ResponseEntity.ok(foundDto);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody T requestData) {
        ResponseDto createdDto = service.create(requestData);
        URI newResourceLocation = getNewResourceLocation(createdDto.id());

        return createResponse(createdDto, newResourceLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> replace(@PathVariable long id, @RequestBody T requestData) {
        ResponseDto replacement = service.replace(id, requestData);

        return ResponseEntity.ok(replacement);
    }

    private URI getNewResourceLocation(long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    private ResponseEntity<ResponseDto> createResponse(ResponseDto createdEntity, URI entityLocation) {
        return ResponseEntity
                .created(entityLocation)
                .body(createdEntity);
    }

}
