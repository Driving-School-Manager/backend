package com.driving.school.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentDetailsDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean active;
}
