package com.medicinebuddy.medicinebuddy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient_medicine")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "patient", "medicineInformation" ,"hours"})
public class PatientMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "amount", length = 10, nullable = false)
    private String amount;

    @Column(name = "taking_form", length = 15, nullable = false)
    private String takingForm;

    @Column(name = "starting_date", nullable = false)
    private LocalDate startingDate;

    @Column(name = "finishing_date")
    private LocalDate finishingDate;

    @Column(name = "not_taken_counter")
    private int notTakenCounter;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "medicine_information_id")
    @JsonIgnore
    private MedicineInformation medicineInformation;

    @OneToMany(mappedBy = "patientMedicine", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Hour> hours;

    public void addHour(Hour hour){
        if(this.hours == null){
            this.hours = new ArrayList<>();
        }
        hour.setPatientMedicine(this);
        hours.add(hour);
    }

    @Override
    public String toString() {
        return "PatientMedicine{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", takingForm='" + takingForm + '\'' +
                ", startingDate=" + startingDate +
                ", finishingDate=" + finishingDate +
                ", notTakenCounter=" + notTakenCounter +
                ", patient=" + patient +
                ", medicineInformation=" + medicineInformation +
                '}';
    }
}
