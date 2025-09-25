package com.example.Hospital_Management_System.Service;

import com.example.Hospital_Management_System.Entity.Doctor;
import com.example.Hospital_Management_System.Exception.ResourceNotFoundException;
import com.example.Hospital_Management_System.Repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor doctor = getDoctorById(id);
        doctor.setName(doctorDetails.getName());
        doctor.setSpecialization(doctorDetails.getSpecialization());
        doctor.setContactNumber(doctorDetails.getContactNumber());
        doctor.setAvailabilityStatus(doctorDetails.getAvailabilityStatus());
        return doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = getDoctorById(id);
        doctorRepository.delete(doctor);
    }
}
