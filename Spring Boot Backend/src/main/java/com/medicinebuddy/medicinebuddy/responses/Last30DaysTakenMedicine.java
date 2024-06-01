package com.medicinebuddy.medicinebuddy.responses;

import java.time.LocalDate;
import java.time.LocalTime;

public class Last30DaysTakenMedicine {

    private LocalTime hour;
    private String name;
    private boolean taken;
    private LocalDate date;

    public Last30DaysTakenMedicine(LocalTime hour, String name, boolean taken, LocalDate date) {
        this.hour = hour;
        this.name = name;
        this.taken = taken;
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
