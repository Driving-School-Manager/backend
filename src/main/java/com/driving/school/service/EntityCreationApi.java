package com.driving.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface EntityCreationApi {
    default URI getNewResourceLocation(long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    default <T> ResponseEntity<T> createResponse(
            T createdEntity,
            URI entityLocation
    ) {
        return ResponseEntity
                .created(entityLocation)
                .body(createdEntity);
    }
}
