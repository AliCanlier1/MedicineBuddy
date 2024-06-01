package com.medicinebuddy.medicinebuddy.services;

import com.medicinebuddy.medicinebuddy.core.utilities.*;
import com.medicinebuddy.medicinebuddy.entities.Buddy;
import com.medicinebuddy.medicinebuddy.entities.Patient;
import com.medicinebuddy.medicinebuddy.repos.BuddyRepository;
import com.medicinebuddy.medicinebuddy.repos.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuddyService {

    private final BuddyRepository buddyRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public BuddyService(BuddyRepository buddyRepository, PatientRepository patientRepository){
        this.buddyRepository = buddyRepository;
        this.patientRepository = patientRepository;
    }

    public ResponseEntity<?> getAllBuddys() {
        List<Buddy> buddys = buddyRepository.findAll();
        if(buddys.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no buddies in the database."));
        }
        else {
            return ResponseEntity.ok(new SuccessDataResult<>(buddys, "Successfully fetched all buddies"));
        }
    }

    public ResponseEntity<Result> logInBuddy(String id, String password) {
        Optional<Buddy> buddy = buddyRepository.findById(id);
        if(buddy.isEmpty() || !buddy.get().getPassword().equals(password)){
            return ResponseEntity.badRequest().body(new ErrorDataResult<>("Bad credentials"));
        }
        return ResponseEntity.ok(new SuccessDataResult<>(buddy, "Welcome "+ buddy.get().getName()));
    }

    public ResponseEntity<Result> addBuddy(Buddy buddy) {
        Optional<Buddy> buddy1 = buddyRepository.findById(buddy.getId());
        if(buddy1.isPresent()){
            return ResponseEntity.badRequest().body(new ErrorResult("The user has already registered."));
        }
        else{
            buddyRepository.save(buddy);
            return ResponseEntity.ok(new SuccessResult("The user which id is "+buddy.getId()+ " has been registered."));
        }
    }

    public ResponseEntity<Result> addPatient(String patientId, String buddyId) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);

        if(optionalPatient.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no patient concerned with this id!"));
        }
        else{
            Patient patient = optionalPatient.get();
            if(patient.getBuddy() != null){
                return ResponseEntity.badRequest().body(new ErrorResult("This patient already has a buddy."));
            }
            Optional<Buddy> buddy = buddyRepository.findById(buddyId);

            patient.setBuddy(buddy.get());
            buddy.get().addPatient(patient);
            buddyRepository.save(buddy.get());
            patientRepository.save(patient);
            return ResponseEntity.ok(new SuccessResult("The patient which id is "+ patientId +" has been added successfully to "+ buddy.get().getId()+" buddy"));
        }
    }

    public ResponseEntity<Result> updateKey(String id, String key) {
        Optional<Buddy> buddy = buddyRepository.findById(id);
        buddy.get().setKey(key);
        buddyRepository.save(buddy.get());
        return ResponseEntity.ok(new SuccessResult());
    }

    public ResponseEntity<?> getAllPatients(String id) {
        Optional<Buddy> buddy = buddyRepository.findById(id);
        List<Patient> patientList = buddy.get().getPatients();
        if(patientList.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no connected patient with this buddy which id is "+ id));
        }
        else{
            return ResponseEntity.ok(new SuccessDataResult<>(patientList, "The list of patient has successfully fetched."));
        }
    }

    public ResponseEntity<?> getPatientsByFilter(String buddyId, String query) {
        List<?> patientsList = patientRepository.findPatientsByFilter(query, buddyId);
        if(patientsList.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no patients related with values that you typed in."));
        }
        else{
            return ResponseEntity.ok(new SuccessDataResult<>(patientsList,"Patients have been fetched successfully."));
        }
    }
}
