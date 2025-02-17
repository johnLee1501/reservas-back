package com.kata.reservas.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentUpdateDTO {
    @Future(message = "La fecha y hora deben estar en el futuro")
    private LocalDateTime dateTime;

    @Size(max = 50, message = "El estado no puede exceder los 50 caracteres")
    private String state;

    private Long professionalId;
}
