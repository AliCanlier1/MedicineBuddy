package com.medicinebuddy.medicinebuddy.repos;

import com.medicinebuddy.medicinebuddy.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

    @Query(value = "FROM Patient WHERE name LIKE %:query% or surname LIKE %:query% or id LIKE %:query% and buddy.id = :buddyId")
    List<?> findPatientsByFilter(String query, String buddyId);

}
