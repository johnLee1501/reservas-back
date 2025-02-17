package com.kata.reservas.service.availability;

import com.kata.reservas.dto.AvailabilityCreateDTO;
import com.kata.reservas.dto.AvailabilityDTO;
import com.kata.reservas.dto.AvailabilityUpdateDTO;

import java.util.List;

public interface AvailabilityService {
    AvailabilityDTO createAvailability(AvailabilityCreateDTO availabilityCreateDTO);
    List<AvailabilityDTO> getAllAvailabilities();
    List<AvailabilityDTO> getAvailabilitiesByProfessionalId(Long professionalId);
    List<AvailabilityDTO> getAvailabilitiesByProfessionalIdAndDate(Long professionalId, String date); // Ex: "2023-10-12"
    AvailabilityDTO getAvailabilityById(Long id);
    AvailabilityDTO updateAvailability(Long id, AvailabilityUpdateDTO availabilityUpdateDTO);
    void deleteAvailability(Long id);
}
