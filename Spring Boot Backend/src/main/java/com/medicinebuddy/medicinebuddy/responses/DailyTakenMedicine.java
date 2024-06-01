package com.medicinebuddy.medicinebuddy.responses;

import java.time.LocalTime;

public class DailyTakenMedicine {

    private LocalTime hour;
    private String name;
    private boolean taken;


    public DailyTakenMedicine(LocalTime hour, String name, boolean taken) {
        this.hour = hour;
        this.name = name;
        this.taken = taken;
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
}
