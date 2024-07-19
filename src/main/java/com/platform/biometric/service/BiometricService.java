package com.platform.biometric.service;

import com.platform.biometric.model.EmployeeInAndOutTrace;
import com.platform.biometric.model.EmployeePresentTimeResponse;
import com.platform.biometric.model.entity.EmployeeTimeTrace;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


public interface BiometricService {
    EmployeeTimeTrace addPassTime(EmployeeInAndOutTrace trace);

    EmployeePresentTimeResponse calculateTotalTime(int empId);

    String passAttendance(LocalDate date);
}
