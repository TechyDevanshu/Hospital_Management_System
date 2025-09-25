package com.example.Hospital_Management_System.Controller;

import com.example.Hospital_Management_System.Entity.Appointment;
import com.example.Hospital_Management_System.Service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    public Appointment bookAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestParam String datetime) {
        LocalDateTime appointmentDateTime = LocalDateTime.parse(datetime);
        return appointmentService.bookAppointment(patientId, doctorId, appointmentDateTime);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PutMapping("/{id}/cancel")
    public String cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return "Appointment has cancelled successfully";
    }

    @PutMapping("/{id}/complete")
    public String completeAppointment(@PathVariable Long id) {
        appointmentService.completeAppointment(id);
        return "Appointment has completed successfully";
    }
}
