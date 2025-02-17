package com.kata.reservas.repository;

import com.kata.reservas.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByUserId(Long userId);
    List<AppointmentEntity> findByProfessionalId(Long professionalId);
    boolean existsByProfessionalIdAndDateTime(Long professionalId, LocalDateTime dateTime);
}
