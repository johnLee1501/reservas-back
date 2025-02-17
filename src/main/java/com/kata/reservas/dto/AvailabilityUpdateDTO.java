package com.kata.reservas.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AvailabilityUpdateDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
