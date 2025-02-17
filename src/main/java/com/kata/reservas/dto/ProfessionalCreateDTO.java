package com.kata.reservas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ProfessionalCreateDTO {
    @NotBlank(message = "User ID es requerido")
    private Long userId;

    @NotBlank(message = "Especialidad es requerida")
    private String specialty;

    private String description;
}
