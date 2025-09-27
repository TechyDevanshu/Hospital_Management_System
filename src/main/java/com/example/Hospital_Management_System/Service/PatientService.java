package com.example.Hospital_Management_System.Service;

import com.example.Hospital_Management_System.Entity.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PatientService {
    Patient addPatient(Patient patient);

    List<Patient> getAllPatients();

    Patient getPatientById(Long id);

    Patient updatePatient(Long id, Patient patient);

    void deletePatient(Long id);


    Patient getPatientByEmail(String username);
}
