package com.medicinebuddy.medicinebuddy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name ="buddy")
@NoArgsConstructor
@AllArgsConstructor
public class Buddy {

    @Id
    @Column(name = "id", length = 15, updatable = false)
    private String id;

    @Column(name = "password",  length = 6, nullable = false)
    private String password;

    @Column(name = "buddy_key", unique = true, nullable = false)
    private String key;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "surname", length = 30, nullable = false)
    private String surname;

    @OneToMany(mappedBy = "buddy", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Patient> patients;

    @OneToMany(mappedBy = "buddy", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BuddyNotification> notifications;

    public void addPatient(Patient patient){
        if(this.patients == null){
            this.patients = new ArrayList<>();
        }
        patient.setBuddy(this);
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

    public void addNotification(BuddyNotification buddyNotification){
        if(this.notifications == null){
            notifications = new ArrayList<>();
        }
        buddyNotification.setBuddy(this);
        notifications.add(buddyNotification);
    }

    @Override
    public String toString() {
        return "Buddy{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
