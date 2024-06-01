package com.medicinebuddy.medicinebuddy.services;

import com.medicinebuddy.medicinebuddy.core.utilities.*;
import com.medicinebuddy.medicinebuddy.entities.MedicineInformation;
import com.medicinebuddy.medicinebuddy.entities.Patient;
import com.medicinebuddy.medicinebuddy.repos.MedicineInformationRepository;
import com.medicinebuddy.medicinebuddy.repos.PatientMedicineRepository;
import com.medicinebuddy.medicinebuddy.repos.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMedicineRepository patientMedicineRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository, PatientMedicineRepository patientMedicineRepository){
        this.patientRepository = patientRepository;
        this.patientMedicineRepository = patientMedicineRepository;
    }

    public ResponseEntity<Result> logInPatient(String id, String password) {
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isEmpty() || !patient.get().getPassword().equals(password)){
            return ResponseEntity.badRequest().body(new ErrorDataResult<>("Bad credentials"));
        }
        return ResponseEntity.ok(new SuccessDataResult<>(patient, "Welcome "+ patient.get().getName()));
    }

    public ResponseEntity<?> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        if(patients.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no patients in the database"));
        }
        else{
            return ResponseEntity.ok(new SuccessDataResult<>(patients, "successfully fetched all patients."));
        }
    }

    public ResponseEntity<Result> addPatient(Patient patient) {
        Optional<Patient> patient1 = patientRepository.findById(patient.getId());
        if(patient1.isPresent()){
            return ResponseEntity.badRequest().body(new ErrorResult("The user has already registered."));
        }
        else{
            Patient savedPatient = new Patient();
            savedPatient.setKey(patient.getKey());
            savedPatient.setId(patient.getId());
            savedPatient.setDateOfBirth(patient.getDateOfBirth());
            savedPatient.setName(patient.getName().substring(0,1).toUpperCase()+ patient.getName().substring(1));
            savedPatient.setSurname(patient.getSurname().substring(0,1).toUpperCase()+ patient.getSurname().substring(1));
            savedPatient.setPassword(patient.getPassword());
            patientRepository.save(savedPatient);
            return ResponseEntity.ok(new SuccessResult("The user which id is "+patient.getId()+ " has been registered."));
        }

    }
    public ResponseEntity<Result> updateKey(String id, String key) {
        Optional<Patient> patient = patientRepository.findById(id);
        patient.get().setKey(key);
        patientRepository.save(patient.get());
        return ResponseEntity.ok(new SuccessResult());
    }

}
