package com.medicinebuddy.medicinebuddy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medicine_information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineInformation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 25, unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "critical", nullable = false)
    private int critical;

    @OneToMany(mappedBy = "medicineInformation", cascade = CascadeType.ALL)
    private List<PatientMedicine> patientMedicines;

    public void addMedicine(PatientMedicine patientMedicine){
        if(this.patientMedicines == null){
            this.patientMedicines = new ArrayList<>();
        }
        patientMedicine.setMedicineInformation(this);
        patientMedicines.add(patientMedicine);
    }

    public boolean deleteMedicine(PatientMedicine patientMedicine){
        if(this.patientMedicines == null || !this.patientMedicines.contains(patientMedicine)){
            return false;
        }
        else{
            patientMedicines.remove(patientMedicine);
            return true;
        }
    }

    @Override
    public String toString() {
        return "MedicineInformation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", critical=" + critical +
                '}';
    }
}
