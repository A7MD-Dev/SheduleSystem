package system.schedulesystem.service;

import system.schedulesystem.exception.AppointmentNotFoundException;
import jakarta.transaction.Transactional;
import system.schedulesystem.model.Appointment;
import system.schedulesystem.model.AppointmentStatus;
import system.schedulesystem.model.Patient;
import system.schedulesystem.model.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public Appointment createAppointment(Appointment appointment) {
        validateAppointmentData(appointment);
        return appointmentRepository.save(appointment);
    }



    public List<Appointment> getAppointmentsByDate(String date) {
        return appointmentRepository.findByAppointmentTime(LocalDate.parse(date));
    }

    public List<Appointment> findAllAppointment() {
        return appointmentRepository.findAll();
    }


    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("#"+appointmentId +"Not Found :/"));
    }

    @Transactional
    public Appointment updateAppointment(Long appointmentId, Appointment appointment) {
        Appointment existingAppointment = getAppointmentById(appointmentId);
        validateUpdatedData(appointment, existingAppointment);

        existingAppointment.setAppointmentTime(appointment.getAppointmentTime());
        existingAppointment.setStatus(appointment.getStatus());
        existingAppointment.setCancellationReason(appointment.getCancellationReason());
        return existingAppointment;
    }
    public void cancelAppointment(Long appointmentId, String reason) {
        Appointment appointment = getAppointmentById(appointmentId);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setCancellationReason(reason);
        appointmentRepository.save(appointment);
    }


    public List<Appointment> getTodayAppointments(String string) {
        LocalDate today = LocalDate.now();
        return appointmentRepository.findByAppointmentTime(today);
    }

    public List<Appointment> getFutureAppointments(String string) {
        LocalDate today = LocalDate.now();
        return appointmentRepository.findByAppointmentTimeAfter(today.atStartOfDay());
    }

    public List<Appointment> getPastAppointments(String string) {
        LocalDate today = LocalDate.now();
        return appointmentRepository.findByAppointmentTimeBefore(today.atStartOfDay());
    }


    private void validateAppointmentData(Appointment appointment) {
        LocalDateTime now = LocalDateTime.now();
        if (appointment.getAppointmentTime().isBefore(now)) {
            throw new IllegalArgumentException("Appointment time Can't be in the past :)");
        }
    }


    private void validateUpdatedData(Appointment updatedAppointment, Appointment existingAppointment) {
        if (!updatedAppointment.getAppointmentTime().equals(existingAppointment.getAppointmentTime())) {
            validateAppointmentData(updatedAppointment);
        }
    }
}
