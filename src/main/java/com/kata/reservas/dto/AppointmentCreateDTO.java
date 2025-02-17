package com.kata.reservas.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentCreateDTO {
    @NotNull(message = "La fecha y hora es requerida")
    @Future(message = "La fecha y hora deben estar en el futuro")
    private LocalDateTime dateTime;

    @NotNull(message = "El ID del usuario es requerido")
    private Long userId;

    @NotNull(message = "El ID del profesional es requerido")
    private Long professionalId;
}
