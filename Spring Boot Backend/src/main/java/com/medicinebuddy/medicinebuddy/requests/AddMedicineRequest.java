package com.medicinebuddy.medicinebuddy.requests;

public class AddMedicineRequest {

    private String name;
    private int isCritical;
    private String description;

    public AddMedicineRequest(String name, int isCritical, String description) {
        this.name = name;
        this.isCritical = isCritical;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsCritical() {
        return isCritical;
    }

    public void setIsCritical(int isCritical) {
        this.isCritical = isCritical;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
