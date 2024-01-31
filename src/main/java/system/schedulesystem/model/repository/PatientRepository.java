package system.schedulesystem.model.repository;

import system.schedulesystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNameContainsIgnoreCase(String name);
    List<Patient> findByNameStartsWithIgnoreCase(char upperCase);
}
