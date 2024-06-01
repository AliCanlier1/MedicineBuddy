package com.medicinebuddy.medicinebuddy.repos;

import com.medicinebuddy.medicinebuddy.entities.PatientNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientNotificationRepository extends JpaRepository<PatientNotification, Integer> {
}
