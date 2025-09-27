package com.example.Hospital_Management_System.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @Past(message = "Date of Birth must be in the past")
    private LocalDate dateOfBirth;

    private String address;

    @NotBlank(message = "Contact number is required")
    @Size(min = 10, max = 15, message = "Contact number must be between 10-15 digits")
    private String contactNumber;

    @Column(length = 2000)
    private String medicalHistory;

}
