package com.kata.reservas.dto;

import lombok.Data;

@Data
public class ProfessionalDto {
    private Long id;
    private Long userId; // ID del UserEntity relacionado
    private String specialty;
    private String description;
    // Puedes incluir otros campos relevantes
}
