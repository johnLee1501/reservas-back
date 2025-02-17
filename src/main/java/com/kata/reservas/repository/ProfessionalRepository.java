package com.kata.reservas.repository;

import com.kata.reservas.entity.ProfessionalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionalRepository extends CrudRepository<ProfessionalEntity, Long> {
    Optional<ProfessionalEntity> findByUserId(Long userId);
}
