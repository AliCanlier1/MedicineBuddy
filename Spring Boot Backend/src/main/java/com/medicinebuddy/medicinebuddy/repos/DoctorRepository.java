package com.medicinebuddy.medicinebuddy.repos;

import com.medicinebuddy.medicinebuddy.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
}
