package com.medicinebuddy.medicinebuddy.repos;

import com.medicinebuddy.medicinebuddy.entities.MedicineInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineInformationRepository extends JpaRepository<MedicineInformation, Integer> {

    MedicineInformation findByName(String name);
    List<?> getMedicineInformationsByNameContains(String medicineName);


}
