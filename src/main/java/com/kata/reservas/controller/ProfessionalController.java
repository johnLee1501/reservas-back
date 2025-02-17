package com.kata.reservas.controller;

import com.kata.reservas.dto.ProfessionalCreateDTO;
import com.kata.reservas.dto.ProfessionalDTO;
import com.kata.reservas.dto.ProfessionalUpdateDTO;
import com.kata.reservas.service.professional.ProfessionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;

@RestController
@RequestMapping("/api/professionals")
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @Autowired
    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    // Crear un nuevo profesional
    @PostMapping
    public ResponseEntity<ProfessionalDTO> createProfessional(@Valid @RequestBody ProfessionalCreateDTO professionalCreateDTO) {
        ProfessionalDTO createdProfessional = professionalService.createProfessional(professionalCreateDTO);
        return new ResponseEntity<>(createdProfessional, HttpStatus.CREATED);
    }

    // Obtener todos los profesionales
    @GetMapping
    public ResponseEntity<List<ProfessionalDTO>> getAllProfessionals() {
        List<ProfessionalDTO> professionals = professionalService.getAllProfessionals();
        return ResponseEntity.ok(professionals);
    }

    // Obtener un profesional por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalDTO> getProfessionalById(@PathVariable Long id) {
        ProfessionalDTO professional = professionalService.getProfessionalById(id);
        return ResponseEntity.ok(professional);
    }

    // Actualizar un profesional
    @PutMapping("/{id}")
    public ResponseEntity<ProfessionalDTO> updateProfessional(@PathVariable Long id, @Valid @RequestBody ProfessionalUpdateDTO professionalUpdateDTO) {
        ProfessionalDTO updatedProfessional = professionalService.updateProfessional(id, professionalUpdateDTO);
        return ResponseEntity.ok(updatedProfessional);
    }

    // Eliminar un profesional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessional(@PathVariable Long id) {
        professionalService.deleteProfessional(id);
        return ResponseEntity.noContent().build();
    }
}
