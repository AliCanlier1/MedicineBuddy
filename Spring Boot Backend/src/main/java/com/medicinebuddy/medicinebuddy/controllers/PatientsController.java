package com.medicinebuddy.medicinebuddy.controllers;

import com.medicinebuddy.medicinebuddy.core.utilities.Result;
import com.medicinebuddy.medicinebuddy.entities.Patient;
import com.medicinebuddy.medicinebuddy.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientsController {

    private final PatientService patientService;

    @Autowired
    public PatientsController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllPatients(){
        return patientService.getAllPatients();
    }

    @GetMapping("/get")
    public ResponseEntity<Result> getPatientById(@RequestParam(name = "patientId") String id, @RequestParam(name = "password") String password){
        return patientService.logInPatient(id, password);
    }

    @PostMapping("/add")
    public ResponseEntity<Result> addPatient(@RequestBody Patient patient){
        return patientService.addPatient(patient);
    }

    @PatchMapping("/updatekey")
    public ResponseEntity<Result> updateTakenMedicines(@RequestParam(name = "patientId") String id, @RequestParam(name = "key") String key){
        return patientService.updateKey(id, key);
    }










}
