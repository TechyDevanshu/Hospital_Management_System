package com.example.Hospital_Management_System.Controller;

import com.example.Hospital_Management_System.Entity.Patient;
import com.example.Hospital_Management_System.Service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @GetMapping
    public List<Patient> geAllPatients(){
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id){
        return patientService.getPatientById(id);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patient){
        return patientService.updatePatient(id,patient);
    }

    @DeleteMapping
    public String deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
        return "Patient deleted successfully with id " + id;
    }
}
