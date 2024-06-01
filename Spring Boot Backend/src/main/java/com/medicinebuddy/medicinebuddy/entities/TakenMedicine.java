package com.medicinebuddy.medicinebuddy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "taken_medicines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TakenMedicine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "taken", nullable = false)
    private boolean taken;

    @Column(name = "checked", nullable = false)
    private boolean checked;

    @Column(name = "checking_time", nullable = false)
    private LocalTime checkingTime;

    @Column(name = "isCritical", nullable = false)
    private boolean isCritical;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "hour_id")
    @JsonIgnore
    private Hour hour;

    @Override
    public String toString() {
        return "TakenMedicine{" +
                "id=" + id +
                ", date=" + date +
                ", taken=" + taken +
                ", hour=" + hour +
                '}';
    }
}
