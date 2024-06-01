package com.medicinebuddy.medicinebuddy.controllers;


import com.medicinebuddy.medicinebuddy.core.utilities.Result;
import com.medicinebuddy.medicinebuddy.entities.Buddy;
import com.medicinebuddy.medicinebuddy.services.BuddyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buddys")
public class BuddysController {

    private final BuddyService buddyService;

    @Autowired
    public BuddysController(BuddyService buddyService){
        this.buddyService = buddyService;
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllBuddys(){
        return buddyService.getAllBuddys();
    }

    @GetMapping("/get")
    public ResponseEntity<Result> getBuddyById(@RequestParam(name = "buddyId") String id, @RequestParam(name = "password") String password){
        return buddyService.logInBuddy(id, password);
    }

    @PostMapping("/add")
    public ResponseEntity<Result> addBuddy(@RequestBody Buddy buddy){
        return buddyService.addBuddy(buddy);
    }

    @PostMapping("/addpatient")
    public ResponseEntity<Result> addPatient(@RequestParam(name = "patientId") String patientId, @RequestParam(name = "buddyId") String buddyId){
        return buddyService.addPatient(patientId, buddyId);
    }

    @PatchMapping("/updatekey")
    public ResponseEntity<Result> updateTakenMedicines(@RequestParam(name = "buddyId") String id, @RequestParam(name = "key") String key){
        return buddyService.updateKey(id, key);
    }

    @GetMapping("/getpatients")
    public ResponseEntity<?> getAllPatients(@RequestParam(name = "buddyId") String id){
        return buddyService.getAllPatients(id);
    }
    @GetMapping("/getpatientsbyfilter")
    public ResponseEntity<?> getPatientsByFiler(@RequestParam(name = "query") String query, @RequestParam(name = "buddyId") String buddyId){
        return buddyService.getPatientsByFilter(buddyId, query);
    }
}
