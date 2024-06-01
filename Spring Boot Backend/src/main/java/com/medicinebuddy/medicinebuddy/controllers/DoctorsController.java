package com.medicinebuddy.medicinebuddy.controllers;

import com.medicinebuddy.medicinebuddy.core.utilities.Result;
import com.medicinebuddy.medicinebuddy.entities.Doctor;
import com.medicinebuddy.medicinebuddy.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorsController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorsController(DoctorService doctorService){
        this.doctorService = doctorService;
    }
    @GetMapping("/getall")
    public ResponseEntity<?> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @GetMapping("/get")
    public ResponseEntity<Result> getDoctorById(@RequestParam(name = "doctorId") String id, @RequestParam(name = "password") String password){
        return doctorService.logInDoctor(id, password);
    }

    @PostMapping("/add")
    public ResponseEntity<Result> addDoctor(@RequestBody Doctor doctor){
        return doctorService.addDoctor(doctor);
    }

    @PostMapping("/addpatient")
    public ResponseEntity<Result> addPatient(@RequestParam(name = "patientId") String patientId, @RequestParam(name = "doctorId") String doctorId){
        return doctorService.addPatient(patientId, doctorId);
    }
    @GetMapping("/getpatients")
    public ResponseEntity<?> getAllPatients(@RequestParam(name = "doctorId") String id){
        return doctorService.getAllPatients(id);
    }
    @GetMapping("/getpatientsbyfilter")
    public ResponseEntity<?> getPatientsByFiler(@RequestParam(name = "query") String query, @RequestParam(name = "doctorId") String doctorId){
        return doctorService.getPatientsByFilter(doctorId, query);
    }
}
