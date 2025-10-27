package com.example.Hospital_Management_System.Repository;

import com.example.Hospital_Management_System.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);

    boolean existsByEmail(String email);
}
