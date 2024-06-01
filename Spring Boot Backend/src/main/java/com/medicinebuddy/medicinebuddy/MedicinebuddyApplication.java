package com.medicinebuddy.medicinebuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedicinebuddyApplication{
	public static void main(String[] args) {
		SpringApplication.run(MedicinebuddyApplication.class, args);
	}
}	
