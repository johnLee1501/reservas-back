package com.kata.reservas.controller;

import com.kata.reservas.dto.AvailabilityCreateDTO;
import com.kata.reservas.dto.AvailabilityDTO;
import com.kata.reservas.dto.AvailabilityUpdateDTO;
import com.kata.reservas.service.availability.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    // Crear una nueva disponibilidad
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    @PostMapping
    public ResponseEntity<AvailabilityDTO> createAvailability(@Valid @RequestBody AvailabilityCreateDTO availabilityCreateDTO) {
        AvailabilityDTO createdAvailability = availabilityService.createAvailability(availabilityCreateDTO);
        return new ResponseEntity<>(createdAvailability, HttpStatus.CREATED);
    }

    // Obtener todas las disponibilidades
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AvailabilityDTO>> getAllAvailabilities() {
        List<AvailabilityDTO> availabilities = availabilityService.getAllAvailabilities();
        return ResponseEntity.ok(availabilities);
    }

    // Obtener disponibilidades por ID del profesional
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<AvailabilityDTO>> getAvailabilitiesByProfessionalId(@PathVariable Long professionalId) {
        List<AvailabilityDTO> availabilities = availabilityService.getAvailabilitiesByProfessionalId(professionalId);
        return ResponseEntity.ok(availabilities);
    }

    // Obtener disponibilidades por ID del profesional y fecha
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    @GetMapping("/professional/{professionalId}/date/{date}")
    public ResponseEntity<List<AvailabilityDTO>> getAvailabilitiesByProfessionalIdAndDate(@PathVariable Long professionalId,
                                                                                          @PathVariable String date) {
        List<AvailabilityDTO> availabilities = availabilityService.getAvailabilitiesByProfessionalIdAndDate(professionalId, date);
        return ResponseEntity.ok(availabilities);
    }

    // Obtener una disponibilidad por ID
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityDTO> getAvailabilityById(@PathVariable Long id) {
        AvailabilityDTO availability = availabilityService.getAvailabilityById(id);
        return ResponseEntity.ok(availability);
    }

    // Actualizar una disponibilidad
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    @PutMapping("/{id}")
    public ResponseEntity<AvailabilityDTO> updateAvailability(@PathVariable Long id,
                                                              @Valid @RequestBody AvailabilityUpdateDTO availabilityUpdateDTO) {
        AvailabilityDTO updatedAvailability = availabilityService.updateAvailability(id, availabilityUpdateDTO);
        return ResponseEntity.ok(updatedAvailability);
    }

    // Eliminar una disponibilidad
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) {
        availabilityService.deleteAvailability(id);
        return ResponseEntity.noContent().build();
    }
}
