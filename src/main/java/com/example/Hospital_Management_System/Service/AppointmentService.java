package com.example.Hospital_Management_System.Service;

import com.example.Hospital_Management_System.Entity.Appointment;
import com.example.Hospital_Management_System.Entity.AppointmentStatus;
import com.example.Hospital_Management_System.Entity.Doctor;
import com.example.Hospital_Management_System.Entity.Patient;
import com.example.Hospital_Management_System.Exception.ResourceNotFoundException;
import com.example.Hospital_Management_System.Repository.AppointmentRepository;
import com.example.Hospital_Management_System.Repository.DoctorRepository;
import com.example.Hospital_Management_System.Repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime dateTime) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        if (!appointmentRepository.findByPatientAndAppointmentDateTime(patient, dateTime).isEmpty()) {
            throw new RuntimeException("Patient already has an appointment at this time");
        }

        if (!appointmentRepository.findByDoctorAndAppointmentDateTime(doctor, dateTime).isEmpty()) {
            throw new RuntimeException("Doctor already has an appointment at this time");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setAppointmentDateTime(dateTime);
        appointment.setDoctor(doctor);
        appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public void completeAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
    }

    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }
}
