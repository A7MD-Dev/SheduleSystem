package system.schedulesystem.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import system.schedulesystem.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>  {
    List<Appointment> findByAppointmentTime(LocalDate date);
    List<Appointment> findByAppointmentTimeAfter(LocalDateTime date);
    List<Appointment> findByAppointmentTimeBefore(LocalDateTime date);
    List<Appointment> findByPatientId(Long patientId);

}
