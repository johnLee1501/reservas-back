package com.kata.reservas.entity;

import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "availabilities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    private ProfessionalEntity professional;
}
