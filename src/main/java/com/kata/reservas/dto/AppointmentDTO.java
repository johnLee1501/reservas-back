package com.kata.reservas.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private Long id;
    private LocalDateTime dateTime;
    private String state;
    private Long userId;
    private Long professionalId;
}
