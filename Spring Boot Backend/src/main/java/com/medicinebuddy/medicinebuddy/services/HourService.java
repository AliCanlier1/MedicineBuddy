package com.medicinebuddy.medicinebuddy.services;

import com.medicinebuddy.medicinebuddy.core.utilities.ErrorResult;
import com.medicinebuddy.medicinebuddy.core.utilities.Result;
import com.medicinebuddy.medicinebuddy.core.utilities.SuccessDataResult;
import com.medicinebuddy.medicinebuddy.entities.Hour;
import com.medicinebuddy.medicinebuddy.repos.HourRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class HourService {

    private final HourRepository hourRepository;

    public HourService(HourRepository hourRepository){
        this.hourRepository = hourRepository;
    }


    public ResponseEntity<Result> addHour(String hour) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.parse(hour, formatter);
        Hour newHour = new Hour();
        newHour.setHour(time);
        hourRepository.save(newHour);
        return ResponseEntity.ok(new SuccessDataResult<>(newHour, newHour+" has been added successfully."));
    }

    public ResponseEntity<?> getAllHours() {
        List<Hour> hours = hourRepository.findAll();
        if(hours.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no hour registered to the database."));
        }
        else{
            return ResponseEntity.ok(new SuccessDataResult<>(hours, "Hours are listed."));
        }
    }
}
