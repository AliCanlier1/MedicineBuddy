package com.medicinebuddy.medicinebuddy.repos;

import com.medicinebuddy.medicinebuddy.entities.PatientMedicine;
import com.medicinebuddy.medicinebuddy.responses.MedicineHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientMedicineRepository extends JpaRepository<PatientMedicine, Integer> {

    @Query(value =
            "SELECT new com.medicinebuddy.medicinebuddy.responses.MedicineHistoryItem(mi.name, pm.startingDate, pm.finishingDate) " +
            "FROM PatientMedicine pm JOIN MedicineInformation mi on pm.medicineInformation.id = mi.id " +
            "WHERE pm.patient.id = :patientId")
    List<MedicineHistoryItem> getPatientMedicineByPatientId(@Param("patientId") String patientId);
}
