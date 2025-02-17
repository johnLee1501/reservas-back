package com.kata.reservas.repository;

import com.kata.reservas.entity.AvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, Long> {
    List<AvailabilityEntity> findByProfessionalId(Long professionalId);
    List<AvailabilityEntity> findByProfessionalIdAndDate(Long professionalId, LocalDate date);
    boolean existsByProfessionalIdAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Long professionalId, LocalDate date, LocalTime endTime, LocalTime startTime);
}
