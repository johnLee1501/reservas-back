package com.kata.reservas.entity;

import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    // Podrías utilizar un Enum para el estado (PENDIENTE, CONFIRMADA, CANCELADA, etc.)
    private String state;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    private ProfessionalEntity professional;
}
