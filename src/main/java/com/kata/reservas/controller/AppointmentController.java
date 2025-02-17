package com.kata.reservas.controller;

import com.kata.reservas.dto.AppointmentCreateDTO;
import com.kata.reservas.dto.AppointmentDTO;
import com.kata.reservas.dto.AppointmentUpdateDTO;
import com.kata.reservas.service.appointment.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Crear una nueva cita
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentCreateDTO appointmentCreateDTO) {
        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentCreateDTO);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    // Obtener todas las citas
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Obtener citas por ID del usuario
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByUserId(@PathVariable Long userId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByUserId(userId);
        return ResponseEntity.ok(appointments);
    }

    // Obtener citas por ID del profesional
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByProfessionalId(@PathVariable Long professionalId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByProfessionalId(professionalId);
        return ResponseEntity.ok(appointments);
    }

    // Obtener una cita por ID
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('PROFESSIONAL')")
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    // Actualizar una cita
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id,
                                                            @Valid @RequestBody AppointmentUpdateDTO appointmentUpdateDTO) {
        AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, appointmentUpdateDTO);
        return ResponseEntity.ok(updatedAppointment);
    }

    // Eliminar una cita
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
