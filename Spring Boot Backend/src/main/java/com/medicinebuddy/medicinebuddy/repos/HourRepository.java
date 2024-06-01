package com.medicinebuddy.medicinebuddy.repos;

import com.medicinebuddy.medicinebuddy.entities.Hour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HourRepository extends JpaRepository<Hour, Integer> {

    @Query(value = "FROM Hour WHERE HOUR(hour) = HOUR(CURRENT_TIME()) AND MINUTE(hour) = MINUTE(CURRENT_TIME())")
    List<Hour> getPatientByTime();



}
