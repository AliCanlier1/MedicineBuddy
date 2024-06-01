package com.medicinebuddy.medicinebuddy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @Column(name = "id", length = 15, updatable = false)
    private String id;

    @Column(name = "password",  length = 6, nullable = false)
    private String password;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "surname", length = 30, nullable = false)
    private String surname;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Patient> patients;

    public void addPatient(Patient patient){
        if(this.patients == null){
            this.patients = new ArrayList<>();
        }
        patient.setDoctor(this);
        patients.add(patient);
    }
    public boolean deletePatient(Patient patient){
        if(this.patients == null || !this.patients.contains(patient)){
            return false;
        }
        else{
            patients.remove(patient);
            return true;
        }
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
