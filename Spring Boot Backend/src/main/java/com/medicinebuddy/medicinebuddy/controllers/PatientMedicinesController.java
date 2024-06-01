package com.medicinebuddy.medicinebuddy.controllers;

import com.medicinebuddy.medicinebuddy.core.utilities.Result;
import com.medicinebuddy.medicinebuddy.requests.AddMedicinetoPatientRequest;
import com.medicinebuddy.medicinebuddy.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patientmedicines")
public class PatientMedicinesController {

    private final MedicineService medicineService;

    @Autowired
    public PatientMedicinesController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping("/addmedicine")
    public ResponseEntity<Result> addMedicine(@RequestBody AddMedicinetoPatientRequest addMedicinetoPatientRequest){
        return medicineService.addMedicine(addMedicinetoPatientRequest);
    }

    @GetMapping("/getmedicineshistory")
    public ResponseEntity<?> getMedicinesHistory(@RequestParam(name = "patientId") String id){
        return medicineService.getMedicineHistory(id);
    }


}
