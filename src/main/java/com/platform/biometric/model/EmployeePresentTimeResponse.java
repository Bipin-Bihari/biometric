package com.platform.biometric.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class EmployeePresentTimeResponse {
    private int employeeId;
    private LocalTime presentHours;
    private LocalDate date;
    private String attendance;
}
