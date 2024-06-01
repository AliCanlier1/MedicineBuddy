package com.medicinebuddy.medicinebuddy.services;

import com.medicinebuddy.medicinebuddy.core.utilities.*;
import com.medicinebuddy.medicinebuddy.entities.Buddy;
import com.medicinebuddy.medicinebuddy.entities.Doctor;
import com.medicinebuddy.medicinebuddy.entities.Patient;
import com.medicinebuddy.medicinebuddy.repos.DoctorRepository;
import com.medicinebuddy.medicinebuddy.repos.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, PatientRepository patientRepository){
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }
    public ResponseEntity<Result> logInDoctor(String id, String password) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isEmpty() || !doctor.get().getPassword().equals(password)){
            return ResponseEntity.badRequest().body(new ErrorDataResult<>("Bad credentials"));
        }
        return ResponseEntity.ok(new SuccessDataResult<>(doctor, "Welcome "+ doctor.get().getName()));
    }

    public ResponseEntity<?> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        if(doctors.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no doctors in the database"));
        }
        else{
            return ResponseEntity.ok(new SuccessDataResult<>(doctors, "successfully fetched all doctors."));
        }
    }

    public ResponseEntity<Result> addDoctor(Doctor doctor) {
        Optional<Doctor> doctor1 = doctorRepository.findById(doctor.getId());
        if(doctor1.isPresent()){
            return ResponseEntity.badRequest().body(new ErrorResult("The user has already registered."));
        }
        else{
            doctorRepository.save(doctor);
            return ResponseEntity.ok(new SuccessResult("The user which id is "+doctor.getId()+ " has been registered."));
        }

    }
    public ResponseEntity<Result> addPatient(String patientId, String doctorId) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);

        if(optionalPatient.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no patient concerned with this id!"));
        }
        else{
            Patient patient = optionalPatient.get();
            if(patient.getDoctor() != null){
                return ResponseEntity.badRequest().body(new ErrorResult("This patient already has a buddy."));
            }
            Optional<Doctor> doctor = doctorRepository.findById(doctorId);

            patient.setDoctor(doctor.get());
            doctor.get().addPatient(patient);
            doctorRepository.save(doctor.get());
            patientRepository.save(patient);
            return ResponseEntity.ok(new SuccessResult("The patient which id is "+ patientId +" has been added successfully to "+ doctor.get().getId()+" doctor."));
        }
    }

    public ResponseEntity<?> getAllPatients(String id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        List<Patient> patientList = doctor.get().getPatients();
        if(patientList.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no connected patient with this buddy which id is "+ id));
        }
        else{
            return ResponseEntity.ok(new SuccessDataResult<>(patientList, "The list of patient has successfully fetched."));
        }
    }

    public ResponseEntity<?> getPatientsByFilter(String doctorId, String query) {
        List<?> patientsList = patientRepository.findPatientsByFilter(query, doctorId);
        if(patientsList.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no patients related with values that you typed in."));
        }
        else{
            return ResponseEntity.ok(new SuccessDataResult<>(patientsList,"Patients have been fetched successfully."));
        }
    }

}
