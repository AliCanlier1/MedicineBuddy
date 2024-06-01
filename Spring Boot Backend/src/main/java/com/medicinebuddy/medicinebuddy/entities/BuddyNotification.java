package com.medicinebuddy.medicinebuddy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "buddy_notification")
@NoArgsConstructor
@AllArgsConstructor
public class BuddyNotification {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "retrieved", nullable = false)
    private int retrieved;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "buddy_id")
    @JsonIgnore
    private Buddy buddy;

    @Override
    public String toString() {
        return "BuddyNotification{" +
                "id=" + id +
                ", retrieved=" + retrieved +
                ", buddy=" + buddy +
                '}';
    }
}
