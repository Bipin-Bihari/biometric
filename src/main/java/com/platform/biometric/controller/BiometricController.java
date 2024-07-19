package com.platform.biometric.controller;

import com.platform.biometric.model.EmployeeInAndOutTrace;
import com.platform.biometric.model.EmployeePresentTimeResponse;
import com.platform.biometric.model.entity.EmployeeTimeTrace;
import com.platform.biometric.service.BiometricService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/employee/")
public class BiometricController {

    @Autowired
    BiometricService biometricService;

    @PostMapping(value = "time")
    public ResponseEntity<EmployeeTimeTrace> EmployeeTrace(@Valid @RequestBody EmployeeInAndOutTrace trace) {
        return new ResponseEntity<>(biometricService.addPassTime(trace), HttpStatus.OK);
    }

    @GetMapping(value = "totalTime/{empId}")
    public ResponseEntity<EmployeePresentTimeResponse> calculateTotalTime(@PathVariable int empId) {
        return new ResponseEntity<>(biometricService.calculateTotalTime(empId), HttpStatus.OK);
    }

    @GetMapping(value = "attendance/pushToKafka",params = "date")
    public ResponseEntity<String> pushKafkaEvent(@RequestParam(value="date") @DateTimeFormat(pattern="MMddyyyy") LocalDate date) {
        System.out.println("date "+date);
        try {
            return new ResponseEntity<>(biometricService.passAttendance(date), HttpStatus.OK);
        }catch (Exception ee){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
