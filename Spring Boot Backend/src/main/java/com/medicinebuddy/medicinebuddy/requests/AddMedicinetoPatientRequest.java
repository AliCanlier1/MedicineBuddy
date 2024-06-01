package com.medicinebuddy.medicinebuddy.requests;

public class AddMedicinetoPatientRequest {

    private String patientId;
    private String medicineName;
    private String takingForm;
    private String takingAmount;
    private String[] hours;

    public AddMedicinetoPatientRequest(String patientId, String medicineName, String takingForm, String takingAmount, String[] hours) {
        this.patientId = patientId;
        this.medicineName = medicineName;
        this.takingForm = takingForm;
        this.takingAmount = takingAmount;
        this.hours = hours;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getTakingForm() {
        return takingForm;
    }

    public void setTakingForm(String takingForm) {
        this.takingForm = takingForm;
    }

    public String getTakingAmount() {
        return takingAmount;
    }

    public void setTakingAmount(String takingAmount) {
        this.takingAmount = takingAmount;
    }

    public String[] getHours() {
        return hours;
    }

    public void setHours(String[] hours) {
        this.hours = hours;
    }
}
