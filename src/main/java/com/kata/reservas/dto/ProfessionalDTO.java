package com.kata.reservas.dto;

import lombok.Data;

@Data
public class ProfessionalDTO {
    private Long id;
    private Long userId;
    private String specialty;
    private String description;
}
