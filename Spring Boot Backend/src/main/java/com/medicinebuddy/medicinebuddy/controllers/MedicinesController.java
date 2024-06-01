package com.medicinebuddy.medicinebuddy.controllers;

import com.medicinebuddy.medicinebuddy.core.utilities.Result;
import com.medicinebuddy.medicinebuddy.requests.AddMedicineRequest;
import com.medicinebuddy.medicinebuddy.services.MedicineInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicines")
public class MedicinesController {

    private final MedicineInformationService medicineInformationService;

    @Autowired
    public MedicinesController(MedicineInformationService medicineInformationService){
        this.medicineInformationService = medicineInformationService;
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllMedicines(){
        return medicineInformationService.getAllMedicines();
    }

    @GetMapping("/get")
    public ResponseEntity<Result> getMedicineByName(@RequestParam(name = "medicineName") String medicineName){
        return medicineInformationService.getMedicineByName(medicineName);
    }

    @PostMapping("/add")
    public ResponseEntity<Result> addMedicine(@RequestBody AddMedicineRequest medicineRequest){
        return medicineInformationService.addMedicine(medicineRequest);
    }

    @GetMapping("/getbycontains")
    public ResponseEntity<?> getMedicinesByContains(@RequestParam(name = "medicineName") String medicineName){
        return medicineInformationService.getMedicineByContains(medicineName);
    }
}
