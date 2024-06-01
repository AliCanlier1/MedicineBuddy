package com.medicinebuddy.medicinebuddy.repos;

import com.medicinebuddy.medicinebuddy.entities.TakenMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TakenMedicineRepository extends JpaRepository<TakenMedicine, Integer> {

    @Query(value = "FROM TakenMedicine WHERE HOUR(checkingTime) = HOUR(CURRENT_TIME()) AND MINUTE(checkingTime) = MINUTE(CURRENT_TIME()) AND checked = false AND date = CURRENT_DATE()")
    List<TakenMedicine> getBuddyByTime();

    @Query(value = "FROM TakenMedicine WHERE checked = true and date = CURRENT_DATE() and hour.patientMedicine.patient.id = :patientId ORDER BY hour.hour DESC")
    List<TakenMedicine> getDailyProgress(@Param("patientId") String patientId);

    @Query(value = "FROM TakenMedicine WHERE checked = true and date >= :thirtyDaysBeforeDate and hour.patientMedicine.patient.id = :patientId ORDER BY date DESC, hour.hour DESC")
    List<TakenMedicine> getLast30DaysProgress(@Param("patientId") String patientId, @Param("thirtyDaysBeforeDate") LocalDate localDate);

    @Query(value = "FROM TakenMedicine WHERE checked = true and taken = false and hour.patientMedicine.medicineInformation.critical = 1 and date = CURRENT_DATE() and isCritical = true")
    List<TakenMedicine> getCriticalNotification();


}
