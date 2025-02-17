package com.kata.reservas.service.professional;

import com.kata.reservas.dto.ProfessionalCreateDTO;
import com.kata.reservas.dto.ProfessionalDTO;
import com.kata.reservas.dto.ProfessionalUpdateDTO;

import java.util.List;

public interface ProfessionalService {
    ProfessionalDTO createProfessional(ProfessionalCreateDTO professionalCreateDTO);

    List<ProfessionalDTO> getAllProfessionals();

    ProfessionalDTO getProfessionalById(Long id);

    ProfessionalDTO updateProfessional(Long id, ProfessionalUpdateDTO professionalUpdateDTO);

    void deleteProfessional(Long id);
}
