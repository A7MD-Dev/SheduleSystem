package system.schedulesystem.service;

import system.schedulesystem.exception.PatientNotFoundException;
import system.schedulesystem.model.Patient;
import system.schedulesystem.model.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    public Patient findPatientById(Long patientId) throws PatientNotFoundException {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isEmpty()) {
            throw new PatientNotFoundException("Patient #" + patientId + " Not Found :/");
        }
        return patient.get();
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long patientId, Patient patient) throws PatientNotFoundException {
        Optional<Patient> existingPatient = patientRepository.findById(patientId);
        if (existingPatient.isEmpty()) {
            throw new PatientNotFoundException("Patient #" + patientId + " Not Found :/");
        }

        existingPatient.get().setName(patient.getName());
        existingPatient.get().setPhone(patient.getPhone());
        existingPatient.get().setEmail(patient.getEmail());
        existingPatient.get().setMedicalHistory(patient.getMedicalHistory());

        return patientRepository.save(existingPatient.get());
    }

    public String getPatientHistory(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient #" + patientId + " Not Found :/"));
        return patient.getMedicalHistory();
    }

    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.findByNameContainsIgnoreCase(name);
    }

    public List<Patient> filterPatientsByStartLetter(Character startLetter) {
        return patientRepository.findByNameStartsWithIgnoreCase(Character.toUpperCase(startLetter));
    }
}
