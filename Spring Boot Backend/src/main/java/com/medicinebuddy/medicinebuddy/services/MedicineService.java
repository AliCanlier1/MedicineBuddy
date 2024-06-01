package com.medicinebuddy.medicinebuddy.services;

import com.medicinebuddy.medicinebuddy.core.utilities.ErrorResult;
import com.medicinebuddy.medicinebuddy.core.utilities.Result;
import com.medicinebuddy.medicinebuddy.core.utilities.SuccessDataResult;
import com.medicinebuddy.medicinebuddy.entities.Hour;
import com.medicinebuddy.medicinebuddy.entities.MedicineInformation;
import com.medicinebuddy.medicinebuddy.entities.Patient;
import com.medicinebuddy.medicinebuddy.entities.PatientMedicine;
import com.medicinebuddy.medicinebuddy.repos.HourRepository;
import com.medicinebuddy.medicinebuddy.repos.MedicineInformationRepository;
import com.medicinebuddy.medicinebuddy.repos.PatientMedicineRepository;
import com.medicinebuddy.medicinebuddy.repos.PatientRepository;
import com.medicinebuddy.medicinebuddy.requests.AddMedicinetoPatientRequest;
import com.medicinebuddy.medicinebuddy.responses.MedicineHistoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    private final PatientMedicineRepository patientMedicineRepository;
    private final MedicineInformationRepository medicineInformationRepository;
    private final PatientRepository patientRepository;
    private final HourRepository hourRepository;


    @Autowired
    public MedicineService(PatientMedicineRepository patientMedicineRepository, MedicineInformationRepository medicineInformationRepository, PatientRepository patientRepository, HourRepository hourRepository){
        this.patientMedicineRepository = patientMedicineRepository;
        this.medicineInformationRepository = medicineInformationRepository;
        this.patientRepository = patientRepository;
        this.hourRepository = hourRepository;
    }

    public ResponseEntity<Result> addMedicine(AddMedicinetoPatientRequest addMedicinetoPatientRequest) {
        Optional<Patient> patient = patientRepository.findById(addMedicinetoPatientRequest.getPatientId());
        Optional<MedicineInformation> medicineInformation = Optional.ofNullable(medicineInformationRepository.findByName(addMedicinetoPatientRequest.getMedicineName()));
        if(medicineInformation.isEmpty()){
            MedicineInformation newMedicine = new MedicineInformation();
            newMedicine.setName(addMedicinetoPatientRequest.getMedicineName());
            newMedicine.setCritical(0);
            medicineInformationRepository.save(newMedicine);
        }
        PatientMedicine newPatientMedicine = new PatientMedicine();
        newPatientMedicine.setMedicineInformation(medicineInformationRepository.findByName(addMedicinetoPatientRequest.getMedicineName()));
        newPatientMedicine.setPatient(patient.get());
        newPatientMedicine.setAmount(addMedicinetoPatientRequest.getTakingAmount());
        newPatientMedicine.setTakingForm(addMedicinetoPatientRequest.getTakingForm());
        LocalDate currentDate = LocalDate.now();
        newPatientMedicine.setStartingDate(currentDate);
        newPatientMedicine.setNotTakenCounter(0);
        List<Hour> hoursList = new ArrayList<>();
        for (String hour : addMedicinetoPatientRequest.getHours()) {
            Hour newHour = new Hour();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime time = LocalTime.parse(hour, formatter);
            newHour.setHour(time);
            newHour.setPatientMedicine(newPatientMedicine);
            hourRepository.save(newHour);
            hoursList.add(newHour);
        }
        newPatientMedicine.setHours(hoursList);
        patientMedicineRepository.save(newPatientMedicine);

        return ResponseEntity.ok(new SuccessDataResult<>(newPatientMedicine, newPatientMedicine+ " has been created successfully."));

    }

    public ResponseEntity<?> getMedicineHistory(String id) {
        List<MedicineHistoryItem> patientMedicines = patientMedicineRepository.getPatientMedicineByPatientId(id);
        if(patientMedicines.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no medicines in medicine history"));
        }
        else{
            return ResponseEntity.ok(new SuccessDataResult<>(patientMedicines, "Medicine history has been fetched successfully."));
        }
    }
}
