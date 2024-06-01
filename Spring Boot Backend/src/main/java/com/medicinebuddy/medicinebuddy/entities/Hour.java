package com.medicinebuddy.medicinebuddy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hour")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hour {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "hour", nullable = false)
    private LocalTime hour;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "patient_medicine_id")
    @JsonIgnore
    private PatientMedicine patientMedicine;

    @OneToMany(mappedBy = "hour", cascade = CascadeType.ALL)
    private List<TakenMedicine> takenMedicines;

    public void addMedicine(TakenMedicine takenMedicine){
        if(this.takenMedicines == null){
            this.takenMedicines = new ArrayList<>();
        }
        takenMedicine.setHour(this);
        takenMedicines.add(takenMedicine);
    }
    public boolean deletePatient(TakenMedicine takenMedicine){
        if(this.takenMedicines == null || !this.takenMedicines.contains(takenMedicine)){
            return false;
        }
        else{
            takenMedicines.remove(takenMedicine);
            return true;
        }
    }

    @Override
    public String toString() {
        return "Hour{" +
                "id=" + id +
                ", hour=" + hour +
                ", patientMedicine=" + patientMedicine +
                '}';
    }
}

