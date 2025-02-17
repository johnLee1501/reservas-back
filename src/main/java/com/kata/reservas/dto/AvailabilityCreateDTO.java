package com.kata.reservas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AvailabilityCreateDTO {
    @NotNull(message = "La fecha es requerida")
    private LocalDate date;

    @NotNull(message = "La hora de inicio es requerida")
    private LocalTime startTime;

    @NotNull(message = "La hora de fin es requerida")
    private LocalTime endTime;

    @NotNull(message = "El ID del profesional es requerido")
    private Long professionalId;
}
