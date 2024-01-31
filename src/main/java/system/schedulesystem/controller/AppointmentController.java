package system.schedulesystem.controller;

import system.schedulesystem.exception.AppointmentNotFoundException;
import system.schedulesystem.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.schedulesystem.model.Patient;
import system.schedulesystem.service.AppointmentService;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/appointments")
@RestController
public class AppointmentController {

    @Autowired
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    @PostMapping("/")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentService.createAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }

    @GetMapping("/")
    public ResponseEntity<List<Appointment>> getAllAppointment() {
        List<Appointment> appointments = appointmentService.findAllAppointment();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<Appointment>> getAppointmentsByDate(@RequestParam("date") String date) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDate(date);
        return ResponseEntity.ok(appointments);
    }


    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long appointmentId) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(appointmentId);
            return ResponseEntity.ok(appointment);
        } catch (AppointmentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long appointmentId, @RequestBody Appointment appointment) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(appointmentId, appointment);
            return ResponseEntity.ok(updatedAppointment);
        } catch (AppointmentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long appointmentId, @RequestParam String reason) {
        appointmentService.cancelAppointment(appointmentId, reason);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/today")
    public ResponseEntity<List<Appointment>> getTodayAppointments() {
        LocalDate today = LocalDate.now();
        return ResponseEntity.ok(appointmentService.getTodayAppointments(today.toString()));
    }

    @GetMapping("/future")
    public ResponseEntity<List<Appointment>> getFutureAppointments() {
        LocalDate today = LocalDate.now();
        return ResponseEntity.ok(appointmentService.getFutureAppointments(today.toString()));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Appointment>> getPastAppointments() {
        LocalDate today = LocalDate.now();
        return ResponseEntity.ok(appointmentService.getPastAppointments(today.toString()));
    }

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        return ResponseEntity.ok(appointments);
    }
}
