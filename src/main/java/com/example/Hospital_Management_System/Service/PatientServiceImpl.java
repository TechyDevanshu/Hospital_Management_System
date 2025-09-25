package com.example.Hospital_Management_System.Service;

import com.example.Hospital_Management_System.Entity.Patient;
import com.example.Hospital_Management_System.Exception.ResourceNotFoundException;
import com.example.Hospital_Management_System.Repository.PatientRepository;
import com.example.Hospital_Management_System.Service.PatientService;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService{
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
    }

    public Patient updatePatient(Long id, Patient patientdetails) {
        Patient patient = getPatientById(id);
        patient.setName(patientdetails.getName());
        patient.setDateOfBirth(patientdetails.getDateOfBirth());
        patient.setAddress(patientdetails.getAddress());
        patient.setContactNumber(patientdetails.getContactNumber());
        patient.setMedicalHistory(patientdetails.getMedicalHistory());
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id){
        Patient patient = getPatientById(id);
        patientRepository.delete(patient);
    }
}
