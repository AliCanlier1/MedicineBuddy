package com.medicinebuddy.medicinebuddy.entities;

import lombok.Data;

import java.util.List;

@Data
public class NotificationMessage {

    private String token;
    private String title;
    private String body;
    private List<Integer> data;
    private String criticalMedicineName;
    private int criticalMedicineCounter;
    private String criticalMedicineDateAndTime;


}
