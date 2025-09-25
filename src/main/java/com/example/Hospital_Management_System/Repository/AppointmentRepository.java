package com.example.Hospital_Management_System.Repository;

import com.example.Hospital_Management_System.Entity.Appointment;
import com.example.Hospital_Management_System.Entity.Doctor;
import com.example.Hospital_Management_System.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment>findByPatientAndAppointmentDateTime(Patient patient, LocalDateTime appointmentDateTime);
    List<Appointment>findByDoctorAndAppointmentDateTime(Doctor doctor, LocalDateTime appointmentDateTime);
}
