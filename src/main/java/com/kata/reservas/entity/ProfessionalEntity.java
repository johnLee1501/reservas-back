package com.kata.reservas.entity;

import jakarta.persistence.*;
        import lombok.*;
        import java.util.List;

@Entity
@Table(name = "professionals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    private String specialty;
    private String description;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailabilityEntity> availabilities;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentEntity> appointments;
}
