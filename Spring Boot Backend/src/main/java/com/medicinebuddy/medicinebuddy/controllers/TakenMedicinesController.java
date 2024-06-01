package com.medicinebuddy.medicinebuddy.controllers;

import com.medicinebuddy.medicinebuddy.core.utilities.Result;
import com.medicinebuddy.medicinebuddy.services.TakenMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/takenmedicines")
public class TakenMedicinesController {

    private final TakenMedicineService takenMedicineService;

    @Autowired
    public TakenMedicinesController(TakenMedicineService takenMedicineService) {
        this.takenMedicineService = takenMedicineService;
    }

    @PatchMapping("/update")
    public ResponseEntity<Result> updateTakenMedicines(@RequestBody String[] takenMedicineIds){
        return takenMedicineService.updateTakenMedicines(takenMedicineIds);
    }

    @GetMapping("/dailyprogress")
    public ResponseEntity<Result> getDailyProgress(@RequestParam(name = "patientId") String patientId){
        return takenMedicineService.getDailyProgress(patientId);
    }

    @GetMapping("/last30daysprogress")
    public ResponseEntity<Result> getLast30DaysProgress(@RequestParam(name = "patientId") String patientId){
        return takenMedicineService.getLast30DaysProgress(patientId);
    }

}
