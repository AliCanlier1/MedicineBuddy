package com.medicinebuddy.medicinebuddy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="patient_notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientNotification {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "retrieved", nullable = false)
    private int retrieved;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    @Override
    public String toString() {
        return "PatientNotification{" +
                "id=" + id +
                ", retrieved=" + retrieved +
                ", patient=" + patient +
                '}';
    }
}
