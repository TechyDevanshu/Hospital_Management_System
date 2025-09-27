package com.example.Hospital_Management_System.Controller;

import com.example.Hospital_Management_System.Entity.Patient;
import com.example.Hospital_Management_System.Entity.Doctor;
import com.example.Hospital_Management_System.Entity.User;
import com.example.Hospital_Management_System.Service.PatientService;
import com.example.Hospital_Management_System.Service.DoctorService;
import com.example.Hospital_Management_System.Service.AppointmentService;
import com.example.Hospital_Management_System.Service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class WebController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final UserService userService;

    public WebController(PatientService patientService, DoctorService doctorService, AppointmentService appointmentService, UserService userService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.userService=userService;
    }

    // ======= HOME / DASHBOARD =======
    @GetMapping("/")
    public String dashboard(HttpSession session) {
        if(session.getAttribute("username") == null) {
            return "home/login"; // redirect to login if not logged in
        }
        return "home/dashboard";
    }

    // ======= LOGIN PAGES =======
    @GetMapping("/login")
    public String login() {
        return "home/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model,
                            HttpSession session) {
        if(userService.login(username, password)) {
            session.setAttribute("username", username); // save user in session
            return "redirect:/"; // go to dashboard
        } else {
            model.addAttribute("error", "Invalid credentials. Please register first.");
            return "home/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // destroy session
        return "home/login";
    }

    // ======= REGISTER PAGES =======
    @GetMapping("/register")
    public String registerPage() {
        return "home/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        boolean success = userService.register(new User(username, password));
        if(success) {
            model.addAttribute("message", "Registration successful! Please login.");
            return "home/login";
        } else {
            model.addAttribute("error", "Username already exists!");
            return "home/register";
        }
    }

    // ================= PATIENT PAGES =================
    @GetMapping("/patients/register")
    public String showPatientForm(Model model, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        model.addAttribute("patient", new Patient());
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients/register";
    }

    @PostMapping("/patients/save")
    public String savePatient(@ModelAttribute Patient patient, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        patientService.addPatient(patient);
        return "redirect:/patients/register";
    }

    @GetMapping("/patients/edit/{id}")
    public String editPatient(@PathVariable Long id, Model model, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        model.addAttribute("patient", patientService.getPatientById(id));
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients/register";
    }

    @PostMapping("/patients/update/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        patientService.updatePatient(id, patient);
        return "redirect:/patients/register";
    }

    @PostMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable Long id, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        patientService.deletePatient(id);
        return "redirect:/patients/register";
    }

    // ================= DOCTOR PAGES =================
    @GetMapping("/doctors/manage")
    public String showDoctorForm(Model model, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        model.addAttribute("doctor", new Doctor());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors/manage";
    }

    @PostMapping("/doctors/save")
    public String saveDoctor(@ModelAttribute Doctor doctor, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        doctorService.addDoctor(doctor);
        return "redirect:/doctors/manage";
    }

    @GetMapping("/doctors/edit/{id}")
    public String editDoctor(@PathVariable Long id, Model model, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        model.addAttribute("doctor", doctorService.getDoctorById(id));
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors/manage";
    }

    @PostMapping("/doctors/update/{id}")
    public String updateDoctor(@PathVariable Long id, @ModelAttribute Doctor doctor, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        doctorService.updateDoctor(id, doctor);
        return "redirect:/doctors/manage";
    }

    @PostMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable Long id, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        doctorService.deleteDoctor(id);
        return "redirect:/doctors/manage";
    }

    // ================= APPOINTMENT PAGES =================
    @GetMapping("/appointments/book")
    public String showAppointmentForm(Model model, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments/book";
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(@RequestParam Long patientId,
                                  @RequestParam Long doctorId,
                                  @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                                  HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        appointmentService.bookAppointment(patientId, doctorId, dateTime);
        return "redirect:/appointments/book";
    }

    @PostMapping("/appointments/complete/{id}")
    public String completeAppointment(@PathVariable Long id, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        appointmentService.completeAppointment(id);
        return "redirect:/appointments/book";
    }

    @PostMapping("/appointments/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id, HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";

        appointmentService.cancelAppointment(id);
        return "redirect:/appointments/book";
    }
}
