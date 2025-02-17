package com.kata.reservas.service.professional;

import com.kata.reservas.dto.ProfessionalCreateDTO;
import com.kata.reservas.dto.ProfessionalDTO;
import com.kata.reservas.dto.ProfessionalUpdateDTO;
import com.kata.reservas.entity.ProfessionalEntity;
import com.kata.reservas.entity.UserEntity;
import com.kata.reservas.repository.ProfessionalRepository;
import com.kata.reservas.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.professionalRepository = professionalRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfessionalDTO createProfessional(ProfessionalCreateDTO professionalCreateDTO) {
        // Verificar si el UserEntity existe
        UserEntity user = userRepository.findById(professionalCreateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si ya existe un profesional para este usuario
        if(professionalRepository.findByUserId(user.getId()).isPresent()) {
            throw new RuntimeException("Profesional ya existe para este usuario");
        }

        ProfessionalEntity professional = new ProfessionalEntity();
        professional.setUser(user);
        professional.setSpecialty(professionalCreateDTO.getSpecialty());
        professional.setDescription(professionalCreateDTO.getDescription());

        ProfessionalEntity savedProfessional = professionalRepository.save(professional);
        return modelMapper.map(savedProfessional, ProfessionalDTO.class);
    }

    @Override
    public List<ProfessionalDTO> getAllProfessionals() {
        List<ProfessionalEntity> professionals = (List<ProfessionalEntity>) professionalRepository.findAll();
        return professionals.stream()
                .map(professional -> modelMapper.map(professional, ProfessionalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfessionalDTO getProfessionalById(Long id) {
        ProfessionalEntity professional = professionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));
        return modelMapper.map(professional, ProfessionalDTO.class);
    }

    @Override
    public ProfessionalDTO updateProfessional(Long id, ProfessionalUpdateDTO professionalUpdateDTO) {
        ProfessionalEntity professional = professionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        if(professionalUpdateDTO.getSpecialty() != null) {
            professional.setSpecialty(professionalUpdateDTO.getSpecialty());
        }
        if(professionalUpdateDTO.getDescription() != null) {
            professional.setDescription(professionalUpdateDTO.getDescription());
        }

        ProfessionalEntity updatedProfessional = professionalRepository.save(professional);
        return modelMapper.map(updatedProfessional, ProfessionalDTO.class);
    }

    @Override
    public void deleteProfessional(Long id) {
        if(!professionalRepository.existsById(id)) {
            throw new RuntimeException("Profesional no encontrado");
        }
        professionalRepository.deleteById(id);
    }
}
