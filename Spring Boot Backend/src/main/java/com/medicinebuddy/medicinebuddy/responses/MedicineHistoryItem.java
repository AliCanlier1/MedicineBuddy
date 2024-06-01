package com.medicinebuddy.medicinebuddy.responses;

import java.time.LocalDate;

public class MedicineHistoryItem {
    private String medicineName;
    private LocalDate startingDate;
    private LocalDate finishingDate;

    public MedicineHistoryItem(String medicineName, LocalDate startingDate, LocalDate finishingDate) {
        this.medicineName = medicineName;
        this.startingDate = startingDate;
        this.finishingDate = finishingDate;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getFinishingDate() {
        return finishingDate;
    }

    public void setFinishingDate(LocalDate finishingDate) {
        this.finishingDate = finishingDate;
    }
}
