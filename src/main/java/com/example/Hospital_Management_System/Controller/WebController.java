package com.example.Hospital_Management_System.Controller;

import com.example.Hospital_Management_System.Entity.Patient;
import com.example.Hospital_Management_System.Entity.Doctor;
import com.example.Hospital_Management_System.Service.PatientService;
import com.example.Hospital_Management_System.Service.DoctorService;
import com.example.Hospital_Management_System.Service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class WebController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    public WebController(PatientService patientService, DoctorService doctorService, AppointmentService appointmentService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    // ================= PATIENT PAGES =================

    @GetMapping("/patients/register")
    public String showPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients/register";
    }

    @PostMapping("/patients/save")
    public String savePatient(@ModelAttribute Patient patient) {
        patientService.addPatient(patient);
        return "redirect:/patients/register";
    }

    @GetMapping("/patients/edit/{id}")
    public String editPatient(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients/register"; // reuse same page
    }

    @PostMapping("/patients/update/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient) {
        patientService.updatePatient(id, patient);
        return "redirect:/patients/register";
    }

    @PostMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients/register";
    }

    // ================= DOCTOR PAGES =================

    @GetMapping("/doctors/manage")
    public String showDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors/manage";
    }

    @PostMapping("/doctors/save")
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctorService.addDoctor(doctor);
        return "redirect:/doctors/manage";
    }

    @GetMapping("/doctors/edit/{id}")
    public String editDoctor(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.getDoctorById(id));
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors/manage"; // reuse same page
    }

    @PostMapping("/doctors/update/{id}")
    public String updateDoctor(@PathVariable Long id, @ModelAttribute Doctor doctor) {
        doctorService.updateDoctor(id, doctor);
        return "redirect:/doctors/manage";
    }

    @PostMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors/manage";
    }

    // ================= APPOINTMENT PAGES =================

    @GetMapping("/appointments/book")
    public String showAppointmentForm(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments/book";
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(@RequestParam Long patientId,
                                  @RequestParam Long doctorId,
                                  @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {

        appointmentService.bookAppointment(patientId, doctorId, dateTime);
        return "redirect:/appointments/book";
    }


    @PostMapping("/appointments/complete/{id}")
    public String completeAppointment(@PathVariable Long id) {
        appointmentService.completeAppointment(id);
        return "redirect:/appointments/book";
    }

    @PostMapping("/appointments/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return "redirect:/appointments/book";
    }
}
