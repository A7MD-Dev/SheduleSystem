package system.schedulesystem.controller;

import system.schedulesystem.exception.PatientNotFoundException;
import system.schedulesystem.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.schedulesystem.service.PatientService;

import java.util.List;

@RequestMapping("/api/patients")
@RestController
public class PatientController {

    @Autowired
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.findAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long patientId) {
        try {
            Patient patient = patientService.findPatientById(patientId);
            return ResponseEntity.ok(patient);
        } catch (PatientNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long patientId, @RequestBody Patient patient) {
        try {
            Patient updatedPatient = patientService.updatePatient(patientId, patient);
            return ResponseEntity.ok(updatedPatient);
        } catch (PatientNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{patientId}/history")
    public ResponseEntity<String> getPatientHistory(@PathVariable Long patientId) {
        String history = patientService.getPatientHistory(patientId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatientsByName(@RequestParam("name") String name) {
        List<Patient> patients = patientService.searchPatientsByName(name);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/filter/{startLetter}")
    public ResponseEntity<List<Patient>> filterPatientsByStartLetter(@PathVariable Character startLetter) {
        List<Patient> patients = patientService.filterPatientsByStartLetter(startLetter);
        return ResponseEntity.ok(patients);
    }

}
