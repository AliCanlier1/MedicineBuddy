package com.medicinebuddy.medicinebuddy.services;

import com.medicinebuddy.medicinebuddy.core.notifications.FirebaseNotificationService;
import com.medicinebuddy.medicinebuddy.entities.*;
import com.medicinebuddy.medicinebuddy.repos.HourRepository;
import com.medicinebuddy.medicinebuddy.repos.PatientMedicineRepository;
import com.medicinebuddy.medicinebuddy.repos.TakenMedicineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class NotificationService {

    private final HourRepository hourRepository;
    private final FirebaseNotificationService firebaseNotificationService;
    private final TakenMedicineRepository takenMedicineRepository;
    private final PatientMedicineRepository patientMedicineRepository;

    @Autowired
    public NotificationService(HourRepository hourRepository, FirebaseNotificationService firebaseNotificationService, TakenMedicineRepository takenMedicineRepository, PatientMedicineRepository patientMedicineRepository) {
        this.hourRepository = hourRepository;
        this.firebaseNotificationService = firebaseNotificationService;
        this.takenMedicineRepository = takenMedicineRepository;
        this.patientMedicineRepository = patientMedicineRepository;
    }

    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void sendNotificationToPatient() {

        List<Hour> hoursList = hourRepository.getPatientByTime();

        if (!hoursList.isEmpty()) {

            Map<Patient, List<Hour>> patientsAndHours = new HashMap<>();

            for (Hour hour : hoursList) {
                Patient patient = hour.getPatientMedicine().getPatient();
                List<Hour> hoursOfPatient = patientsAndHours.get(patient);
                if (hoursOfPatient == null) {
                    hoursOfPatient = new ArrayList<>();
                }
                hoursOfPatient.add(hour);
                patientsAndHours.put(patient, hoursOfPatient);
            }

            Map<Patient, List<Integer>> patientsAndTakenMedicinesIds = new HashMap<>();

            for(Map.Entry<Patient, List<Hour>> entry : patientsAndHours.entrySet()){
                List<Integer> takenMedicinesIds = new ArrayList<>();
                List<Hour> hoursOfPatient = entry.getValue();
                for(Hour hour : hoursOfPatient){
                    TakenMedicine takenMedicine = new TakenMedicine();
                    LocalDate currentDate = LocalDate.now();
                    takenMedicine.setDate(currentDate);
                    Optional<Hour> takenMedicineHour = hourRepository.findById(hour.getId());
                    takenMedicine.setHour(takenMedicineHour.get());
                    takenMedicine.setTaken(false);
                    takenMedicine.setChecked(false);
                    takenMedicine.setCritical(takenMedicineHour.get().getPatientMedicine().getMedicineInformation().getCritical() == 1);
                    takenMedicine.setCheckingTime(takenMedicineHour.get().getHour().plusMinutes(1));
                    takenMedicineRepository.save(takenMedicine);
                    takenMedicinesIds.add(takenMedicine.getId());
                }
                patientsAndTakenMedicinesIds.put(entry.getKey(), takenMedicinesIds);
            }

            for(Map.Entry<Patient, List<Integer>> entry : patientsAndTakenMedicinesIds.entrySet()){
                NotificationMessage notificationMessage = new NotificationMessage();
                notificationMessage.setToken(entry.getKey().getKey());
                notificationMessage.setData(entry.getValue());
                firebaseNotificationService.sendNotificationtoPatient(notificationMessage);
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void sendNotificationToBuddy(){

        List<TakenMedicine> takenMedicinesList = takenMedicineRepository.getBuddyByTime();

        if(!takenMedicinesList.isEmpty()){

            for(TakenMedicine takenMedicine : takenMedicinesList){
                Optional<TakenMedicine> checkedTakenMedicine = takenMedicineRepository.findById(takenMedicine.getId());
                checkedTakenMedicine.get().setChecked(true);
                takenMedicineRepository.save(checkedTakenMedicine.get());
            }

            Map<Buddy, Map<Patient, List<Integer>>> buddyListMap = new HashMap<>();

            for (TakenMedicine takenMedicine : takenMedicinesList) {
                Buddy buddy = takenMedicine.getHour().getPatientMedicine().getPatient().getBuddy();
                Patient patient = takenMedicine.getHour().getPatientMedicine().getPatient();
                int medicineId = takenMedicine.getId();

                Map<Patient, List<Integer>> patientsOfBuddy = buddyListMap.get(buddy);
                if (patientsOfBuddy == null) {
                    patientsOfBuddy = new HashMap<>();
                    buddyListMap.put(buddy, patientsOfBuddy);
                }
                List<Integer> takenMedicines = patientsOfBuddy.get(patient);
                if (takenMedicines == null) {
                    takenMedicines = new ArrayList<>();
                    patientsOfBuddy.put(patient, takenMedicines);
                }
                takenMedicines.add(medicineId);
            }
            for(Map.Entry<Buddy, Map<Patient, List<Integer>>> buddyMapEntry : buddyListMap.entrySet()){
                Map<Patient, List<Integer>> patientsOfBuddy = buddyMapEntry.getValue();
                Set<Patient> takenMedicinePatientSet = new HashSet<>();
                List<Integer> takenMedicineIdList = new ArrayList<>();
                for (Map.Entry<Patient, List<Integer>> patientListEntry : patientsOfBuddy.entrySet()){
                    takenMedicinePatientSet.add(patientListEntry.getKey());
                    for(int i : patientListEntry.getValue()){
                        takenMedicineIdList.add(i);
                    }
                }
                NotificationMessage notificationMessage = new NotificationMessage();
                notificationMessage.setToken(buddyMapEntry.getKey().getKey());
                notificationMessage.setData(takenMedicineIdList);
                firebaseNotificationService.sendNotificationtoBuddy(notificationMessage, takenMedicinePatientSet);
            }
        }

    }

    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void sendCriticalNotification(){
        List<TakenMedicine> notTakenCriticalMedicineList = takenMedicineRepository.getCriticalNotification();
        if(!notTakenCriticalMedicineList.isEmpty()){
            for(TakenMedicine takenMedicineIterator : notTakenCriticalMedicineList){
                Optional<TakenMedicine> takenMedicine = takenMedicineRepository.findById(takenMedicineIterator.getId());
                Optional<PatientMedicine> patientMedicine = patientMedicineRepository.findById(takenMedicine.get().getHour().getPatientMedicine().getId());
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = takenMedicine.get().getHour().getHour();
                LocalDateTime currentTime = LocalDateTime.of(localDate, localTime);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM");
                String formattedDate = currentTime.format(formatter);
                takenMedicine.get().setCritical(false);
                int newNotTakenCounter = patientMedicine.get().getNotTakenCounter()+1;
                patientMedicine.get().setNotTakenCounter(newNotTakenCounter);
                patientMedicineRepository.save(patientMedicine.get());
                takenMedicineRepository.save(takenMedicine.get());
                NotificationMessage patientNotificationMessage = new NotificationMessage();
                patientNotificationMessage.setToken(patientMedicine.get().getPatient().getKey());
                patientNotificationMessage.setCriticalMedicineCounter(newNotTakenCounter);
                patientNotificationMessage.setCriticalMedicineName(patientMedicine.get().getMedicineInformation().getName());
                patientNotificationMessage.setCriticalMedicineDateAndTime(formattedDate);
                firebaseNotificationService.sendCriticalNotificationToPatient(patientNotificationMessage);
                NotificationMessage buddyNotificationMessage = new NotificationMessage();
                buddyNotificationMessage.setToken(patientMedicine.get().getPatient().getBuddy().getKey());
                buddyNotificationMessage.setCriticalMedicineName(patientMedicine.get().getMedicineInformation().getName());
                buddyNotificationMessage.setCriticalMedicineCounter(newNotTakenCounter);
                buddyNotificationMessage.setCriticalMedicineDateAndTime(formattedDate);
                firebaseNotificationService.sendCriticalNotificationToBuddy(buddyNotificationMessage, patientMedicine.get().getPatient().getName()+ " " + patientMedicine.get().getPatient().getSurname());
            }
        }
    }
}
