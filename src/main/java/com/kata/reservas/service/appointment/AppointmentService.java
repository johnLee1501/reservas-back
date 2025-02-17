package com.kata.reservas.service.appointment;

import com.kata.reservas.dto.AppointmentCreateDTO;
import com.kata.reservas.dto.AppointmentDTO;
import com.kata.reservas.dto.AppointmentUpdateDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentCreateDTO appointmentCreateDTO);
    List<AppointmentDTO> getAllAppointments();
    List<AppointmentDTO> getAppointmentsByUserId(Long userId);
    List<AppointmentDTO> getAppointmentsByProfessionalId(Long professionalId);
    AppointmentDTO getAppointmentById(Long id);
    AppointmentDTO updateAppointment(Long id, AppointmentUpdateDTO appointmentUpdateDTO);
    void deleteAppointment(Long id);
}
