package com.kata.reservas.service.availability;

import com.kata.reservas.dto.AvailabilityCreateDTO;
import com.kata.reservas.dto.AvailabilityDTO;
import com.kata.reservas.dto.AvailabilityUpdateDTO;
import com.kata.reservas.entity.AvailabilityEntity;
import com.kata.reservas.entity.ProfessionalEntity;
import com.kata.reservas.repository.AvailabilityRepository;
import com.kata.reservas.repository.ProfessionalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final ProfessionalRepository professionalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository,
                                   ProfessionalRepository professionalRepository,
                                   ModelMapper modelMapper) {
        this.availabilityRepository = availabilityRepository;
        this.professionalRepository = professionalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public AvailabilityDTO createAvailability(AvailabilityCreateDTO availabilityCreateDTO) {
        // Verificar si el profesional existe
        ProfessionalEntity professional = professionalRepository.findById(availabilityCreateDTO.getProfessionalId())
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        // Verificar solapamiento de disponibilidad
        boolean hasOverlap = availabilityRepository.existsByProfessionalIdAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                professional.getId(),
                availabilityCreateDTO.getDate(),
                availabilityCreateDTO.getEndTime(),
                availabilityCreateDTO.getStartTime()
        );

        if (hasOverlap) {
            throw new RuntimeException("Ya existe una disponibilidad que se solapa con el rango de tiempo especificado");
        }

        // Crear la disponibilidad
        AvailabilityEntity availability = new AvailabilityEntity();
        availability.setDate(availabilityCreateDTO.getDate());
        availability.setStartTime(availabilityCreateDTO.getStartTime());
        availability.setEndTime(availabilityCreateDTO.getEndTime());
        availability.setProfessional(professional);

        AvailabilityEntity savedAvailability = availabilityRepository.save(availability);
        return modelMapper.map(savedAvailability, AvailabilityDTO.class);
    }

    @Override
    public List<AvailabilityDTO> getAllAvailabilities() {
        List<AvailabilityEntity> availabilities = availabilityRepository.findAll();
        return availabilities.stream()
                .map(availability -> modelMapper.map(availability, AvailabilityDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailabilityDTO> getAvailabilitiesByProfessionalId(Long professionalId) {
        List<AvailabilityEntity> availabilities = availabilityRepository.findByProfessionalId(professionalId);
        return availabilities.stream()
                .map(availability -> modelMapper.map(availability, AvailabilityDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailabilityDTO> getAvailabilitiesByProfessionalIdAndDate(Long professionalId, String date) {
        LocalDate localDate = LocalDate.parse(date); // Asegúrate de que el formato de la fecha sea correcto
        List<AvailabilityEntity> availabilities = availabilityRepository.findByProfessionalIdAndDate(professionalId, localDate);
        return availabilities.stream()
                .map(availability -> modelMapper.map(availability, AvailabilityDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AvailabilityDTO getAvailabilityById(Long id) {
        AvailabilityEntity availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disponibilidad no encontrada"));
        return modelMapper.map(availability, AvailabilityDTO.class);
    }

    @Override
    @Transactional
    public AvailabilityDTO updateAvailability(Long id, AvailabilityUpdateDTO availabilityUpdateDTO) {
        AvailabilityEntity availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disponibilidad no encontrada"));

        if (availabilityUpdateDTO.getDate() != null) {
            availability.setDate(availabilityUpdateDTO.getDate());
        }

        if (availabilityUpdateDTO.getStartTime() != null) {
            availability.setStartTime(availabilityUpdateDTO.getStartTime());
        }

        if (availabilityUpdateDTO.getEndTime() != null) {
            availability.setEndTime(availabilityUpdateDTO.getEndTime());
        }

        // Verificar solapamiento de disponibilidad después de la actualización
        ProfessionalEntity professional = availability.getProfessional();

        boolean hasOverlap = availabilityRepository.existsByProfessionalIdAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                professional.getId(),
                availability.getDate(),
                availability.getEndTime(),
                availability.getStartTime()
        );

        if (hasOverlap) {
            throw new RuntimeException("Ya existe una disponibilidad que se solapa con el rango de tiempo especificado");
        }

        AvailabilityEntity updatedAvailability = availabilityRepository.save(availability);
        return modelMapper.map(updatedAvailability, AvailabilityDTO.class);
    }

    @Override
    @Transactional
    public void deleteAvailability(Long id) {
        if (!availabilityRepository.existsById(id)) {
            throw new RuntimeException("Disponibilidad no encontrada");
        }
        availabilityRepository.deleteById(id);
    }
}
