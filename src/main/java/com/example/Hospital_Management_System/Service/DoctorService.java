package com.example.Hospital_Management_System.Service;

import com.example.Hospital_Management_System.Entity.Doctor;

import javax.print.Doc;
import java.util.List;

public interface DoctorService {
    Doctor addDoctor(Doctor doctor);
    List<Doctor>getAllDoctors();
    Doctor getDoctorById(Long id);
    Doctor updateDoctor(Long id, Doctor doctor);
    void deleteDoctor(Long id);
}
