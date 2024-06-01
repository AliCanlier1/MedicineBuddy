package com.medicinebuddy.medicinebuddy.services;

import com.medicinebuddy.medicinebuddy.core.utilities.ErrorResult;
import com.medicinebuddy.medicinebuddy.core.utilities.Result;
import com.medicinebuddy.medicinebuddy.core.utilities.SuccessDataResult;
import com.medicinebuddy.medicinebuddy.entities.Patient;
import com.medicinebuddy.medicinebuddy.entities.TakenMedicine;
import com.medicinebuddy.medicinebuddy.repos.TakenMedicineRepository;
import com.medicinebuddy.medicinebuddy.responses.DailyTakenMedicine;
import com.medicinebuddy.medicinebuddy.responses.Last30DaysTakenMedicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TakenMedicineService {

    private final TakenMedicineRepository takenMedicineRepository;



    @Autowired
    public TakenMedicineService(TakenMedicineRepository takenMedicineRepository){
        this.takenMedicineRepository = takenMedicineRepository;
    }

    public ResponseEntity<Result> updateTakenMedicines(String[] takenMedicineIds) {
        for (String takenMedicineId : takenMedicineIds) {
            Optional<TakenMedicine> takenMedicine = takenMedicineRepository.findById(Integer.parseInt(takenMedicineId));
            takenMedicine.get().setTaken(true);
            takenMedicine.get().setChecked(true);
            takenMedicineRepository.save(takenMedicine.get());
        }
        return ResponseEntity.ok(new SuccessDataResult<>(takenMedicineIds, Arrays.toString(takenMedicineIds) +" has been changed successfully"));
    }

    public ResponseEntity<Result> getDailyProgress(String patientId) {
        List<TakenMedicine> takenMedicines = takenMedicineRepository.getDailyProgress(patientId);
        if(takenMedicines.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no data related with this patient."));
        }
        else{
            List<DailyTakenMedicine> dailyTakenMedicines = new ArrayList<>();
            for(TakenMedicine takenMedicine : takenMedicines){
                DailyTakenMedicine dailyTakenMedicine = new DailyTakenMedicine(takenMedicine.getHour().getHour(), takenMedicine.getHour().getPatientMedicine().getMedicineInformation().getName(), takenMedicine.isTaken());
                dailyTakenMedicines.add(dailyTakenMedicine);
            }
            return ResponseEntity.ok(new SuccessDataResult<>(dailyTakenMedicines, "Daily progress has been fetched successfully."));
        }
    }

    public ResponseEntity<Result> getLast30DaysProgress(String patientId) {
        LocalDate thirtyDaysBeforeDate = LocalDate.now().minus(Period.ofDays(30));
        List<TakenMedicine> takenMedicines = takenMedicineRepository.getLast30DaysProgress(patientId, thirtyDaysBeforeDate);
        if(takenMedicines.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no data related with this patient."));
        }
        else{
            List<Last30DaysTakenMedicine> last30DaysTakenMedicines = new ArrayList<>();
            for(TakenMedicine takenMedicine : takenMedicines){
                Last30DaysTakenMedicine last30DaysTakenMedicine = new Last30DaysTakenMedicine(takenMedicine.getHour().getHour(), takenMedicine.getHour().getPatientMedicine().getMedicineInformation().getName(), takenMedicine.isTaken(), takenMedicine.getDate());
                last30DaysTakenMedicines.add(last30DaysTakenMedicine);
            }
            return ResponseEntity.ok(new SuccessDataResult<>(last30DaysTakenMedicines, "Last 30 days progress has been fetched successfully."));
        }
    }
}
