package com.medicinebuddy.medicinebuddy.services;

import com.medicinebuddy.medicinebuddy.core.utilities.ErrorResult;
import com.medicinebuddy.medicinebuddy.core.utilities.Result;
import com.medicinebuddy.medicinebuddy.core.utilities.SuccessDataResult;
import com.medicinebuddy.medicinebuddy.core.utilities.SuccessResult;
import com.medicinebuddy.medicinebuddy.entities.MedicineInformation;
import com.medicinebuddy.medicinebuddy.repos.MedicineInformationRepository;
import com.medicinebuddy.medicinebuddy.requests.AddMedicineRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineInformationService {

    private final MedicineInformationRepository medicineInformationRepository;

    @Autowired
    public MedicineInformationService(MedicineInformationRepository medicineInformationRepository){
        this.medicineInformationRepository = medicineInformationRepository;
    }

    public ResponseEntity<?> getAllMedicines() {
        List<MedicineInformation> medicines = medicineInformationRepository.findAll();
        if(medicines.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no medicines in the database."));
        }
        else{
            return ResponseEntity.ok(new SuccessDataResult<>(medicines, "Successfully fetched all the medicines."));
        }
    }


    public ResponseEntity<Result> getMedicineByName(String medicineName) {
        Optional<MedicineInformation> medicine = Optional.ofNullable(medicineInformationRepository.findByName(medicineName));
        if(medicine.isPresent()){
            return ResponseEntity.ok(new SuccessDataResult<>(medicine.get(), medicine.get().getName()+ " medicine has been fetched successfully."));
        }
        else{
            return ResponseEntity.badRequest().body(new ErrorResult(medicineName + " has not been found in the database."));
        }
    }

    public ResponseEntity<Result> addMedicine(AddMedicineRequest medicineRequest) {
        Optional<MedicineInformation> medicine = Optional.ofNullable(medicineInformationRepository.findByName(medicineRequest.getName()));
        if(medicine.isPresent()){
            return ResponseEntity.badRequest().body(new ErrorResult(medicine.get().getName() + " medicine has already been in the database."));
        }
        else{
            MedicineInformation newMedicine = new MedicineInformation();
            newMedicine.setName(medicineRequest.getName());
            newMedicine.setCritical(medicineRequest.getIsCritical());
            newMedicine.setDescription(medicineRequest.getDescription());
            medicineInformationRepository.save(newMedicine);
            return ResponseEntity.ok(new SuccessResult(medicineRequest.getName()+ " has been saved successfully!"));

        }
    }

    public ResponseEntity<?> getMedicineByContains(String medicineName) {
        List<?> medicines = medicineInformationRepository.getMedicineInformationsByNameContains(medicineName);
        if(medicines.size() == 0){
            return ResponseEntity.badRequest().body(new ErrorResult("There are no medicines related to this name"));
        }
        else{
            return ResponseEntity.ok(new SuccessDataResult<>(medicines, "Related medicines are listed"));
        }
    }
}
