package com.medicinebuddy.medicinebuddy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @Column(name = "id", length = 15, updatable = false)
    private String id;

    @Column(name = "password",  length = 6, nullable = false)
    private String password;

    @Column(name = "patient_key", unique = true, nullable = false)
    private String key;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "surname", length = 30, nullable = false)
    private String surname;

    @Column(name = "date_of_birth", nullable = false)
    private int dateOfBirth;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "buddy_id")
    @JsonIgnore
    private Buddy buddy;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PatientNotification> notifications;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PatientMedicine> patientMedicines;

    public void addMedicine(PatientMedicine patientMedicine){
        if(this.patientMedicines == null){
            this.patientMedicines = new ArrayList<>();
        }
        patientMedicine.setPatient(this);
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
    public void addNotification(PatientNotification patientNotification){
        if(this.notifications == null){
            notifications = new ArrayList<>();
        }
        patientNotification.setPatient(this);
        notifications.add(patientNotification);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", doctor=" + doctor +
                ", buddy=" + buddy +
                '}';
    }
}
