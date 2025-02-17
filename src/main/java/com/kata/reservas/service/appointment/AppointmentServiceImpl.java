package com.kata.reservas.service.appointment;

import com.kata.reservas.dto.AppointmentCreateDTO;
import com.kata.reservas.dto.AppointmentDTO;
import com.kata.reservas.dto.AppointmentUpdateDTO;
import com.kata.reservas.entity.AppointmentEntity;
import com.kata.reservas.entity.AvailabilityEntity;
import com.kata.reservas.entity.ProfessionalEntity;
import com.kata.reservas.entity.UserEntity;
import com.kata.reservas.repository.AppointmentRepository;
import com.kata.reservas.repository.ProfessionalRepository;
import com.kata.reservas.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  UserRepository userRepository,
                                  ProfessionalRepository professionalRepository,
                                  ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.professionalRepository = professionalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public AppointmentDTO createAppointment(AppointmentCreateDTO appointmentCreateDTO) {
        // Verificar si el usuario existe
        UserEntity user = userRepository.findById(appointmentCreateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si el profesional existe
        ProfessionalEntity professional = professionalRepository.findById(appointmentCreateDTO.getProfessionalId())
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        // Verificar disponibilidad
        boolean isAvailable = !appointmentRepository.existsByProfessionalIdAndDateTime(
                professional.getId(),
                appointmentCreateDTO.getDateTime()
        );

        if (!isAvailable) {
            throw new RuntimeException("La profesional no está disponible en la fecha y hora especificadas");
        }

        // Crear la cita
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setDateTime(appointmentCreateDTO.getDateTime());
        appointment.setState("PENDIENTE"); // Estado inicial
        appointment.setUser(user);
        appointment.setProfessional(professional);

        AppointmentEntity savedAppointment = appointmentRepository.save(appointment);
        return modelMapper.map(savedAppointment, AppointmentDTO.class);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<AppointmentEntity> appointments = appointmentRepository.findAll();
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByUserId(Long userId) {
        List<AppointmentEntity> appointments = appointmentRepository.findByUserId(userId);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByProfessionalId(Long professionalId) {
        List<AppointmentEntity> appointments = appointmentRepository.findByProfessionalId(professionalId);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        return modelMapper.map(appointment, AppointmentDTO.class);
    }

    @Override
    @Transactional
    public AppointmentDTO updateAppointment(Long id, AppointmentUpdateDTO appointmentUpdateDTO) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (appointmentUpdateDTO.getDateTime() != null) {
            // Verificar disponibilidad
            boolean isAvailable = !appointmentRepository.existsByProfessionalIdAndDateTime(
                    appointment.getProfessional().getId(),
                    appointmentUpdateDTO.getDateTime()
            );

            if (!isAvailable) {
                throw new RuntimeException("La profesional no está disponible en la nueva fecha y hora especificadas");
            }
            appointment.setDateTime(appointmentUpdateDTO.getDateTime());
        }

        if (appointmentUpdateDTO.getState() != null) {
            appointment.setState(appointmentUpdateDTO.getState());
        }

        if (appointmentUpdateDTO.getProfessionalId() != null) {
            ProfessionalEntity professional = professionalRepository.findById(appointmentUpdateDTO.getProfessionalId())
                    .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));
            appointment.setProfessional(professional);
        }

        AppointmentEntity updatedAppointment = appointmentRepository.save(appointment);
        return modelMapper.map(updatedAppointment, AppointmentDTO.class);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada");
        }
        appointmentRepository.deleteById(id);
    }
}
