package com.medicinebuddy.medicinebuddy.controllers;

import com.medicinebuddy.medicinebuddy.core.utilities.Result;
import com.medicinebuddy.medicinebuddy.services.HourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/hours")
public class HoursController {

    private final HourService hourService;


    public HoursController(HourService hourService) {
        this.hourService = hourService;
    }
    @PostMapping("/add")
    public ResponseEntity<Result> addHour(@RequestParam(name = "hour") String hour){
        return hourService.addHour(hour);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllHours(){
        return hourService.getAllHours();
    }
}
