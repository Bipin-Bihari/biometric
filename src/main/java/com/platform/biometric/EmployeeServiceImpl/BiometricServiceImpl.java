package com.platform.biometric.EmployeeServiceImpl;

import com.platform.biometric.exception.EmployeeNotFoundException;
import com.platform.biometric.model.EmployeeInAndOutTrace;
import com.platform.biometric.model.EmployeePresentTimeResponse;
import com.platform.biometric.model.entity.EmployeeTimeTrace;
import com.platform.biometric.repository.BiometricRepository;
import com.platform.biometric.repository.EmployeeRepository;
import com.platform.biometric.service.BiometricService;
import com.platform.biometric.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BiometricServiceImpl implements BiometricService {

    @Autowired
    BiometricRepository biometricRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    KafkaMessagePublisher kafkaMessagePublisher;

    @Override
    public EmployeeTimeTrace addPassTime(EmployeeInAndOutTrace trace) {

        //check for employee
        validateEmpDetails(trace.getEmployeeId());

        // add gate passing details
        EmployeeTimeTrace e = new EmployeeTimeTrace();
        e.setEmpId(trace.getEmployeeId());
        e.setTime(LocalTime.now());
        e.setDate(LocalDate.now());
        e.setStatus(trace.getStatus());
        return biometricRepository.save(e);
    }

    @Override
    public EmployeePresentTimeResponse calculateTotalTime(int empId) {
        validateEmpDetails(empId);
        List<EmployeeTimeTrace> eList = biometricRepository.findByEmpIdAndDate(empId, LocalDate.now());
        if (eList.isEmpty()) {
            throw new EmployeeNotFoundException("Employee Not Present");
        }
        List<EmployeeTimeTrace> sortedByTime = eList.stream().sorted(Comparator.comparing(EmployeeTimeTrace::getTime)).toList();
        return employeeTotalTime(sortedByTime);
    }

    @Override
    public String passAttendance(LocalDate date) {
        Map<Integer, EmployeePresentTimeResponse> empPresent = findAllEmployeePresentTime(date);
        for (Map.Entry<Integer, EmployeePresentTimeResponse> entry : empPresent.entrySet()) {
            kafkaMessagePublisher.sendMessageToTopic(entry.getKey().toString(), entry.getValue());
        }
        return "Message Publish successfully";
    }

    private Map<Integer, EmployeePresentTimeResponse> findAllEmployeePresentTime(LocalDate date) {
        List<EmployeeTimeTrace> employeesTimeTrace = biometricRepository.findByDate(date);

        Map<Integer, EmployeePresentTimeResponse> result = new HashMap<>();

        Map<Integer, List<EmployeeTimeTrace>> map = employeesTimeTrace.stream().collect(Collectors.groupingBy(EmployeeTimeTrace::getEmpId));
        for (Map.Entry<Integer, List<EmployeeTimeTrace>> entry : map.entrySet()) {
            EmployeePresentTimeResponse presentTimeResponse = employeeTotalTime(entry.getValue());
            result.put(presentTimeResponse.getEmployeeId(), presentTimeResponse);
        }
        return result;
    }

    private EmployeePresentTimeResponse employeeTotalTime(List<EmployeeTimeTrace> value) {
        long totalSeconds = 0L;
        LocalTime var = LocalTime.of(0, 0, 0);
        for (int i = 0; i < value.size(); i++) {
            long seconds = 0L;
            if (value.get(i).getStatus().equals("In") && Objects.equals(var, LocalTime.of(0, 0, 0))) {
                var = value.get(i).getTime();
            } else if (value.get(i).getStatus().equals("Out") && !Objects.equals(var, LocalTime.of(0, 0, 0))) {
                seconds = Duration.between(var, value.get(i).getTime()).getSeconds();
                var = LocalTime.of(0, 0, 0);
            }
            totalSeconds = totalSeconds + seconds;
        }
        LocalTime totalTime = LocalTime.parse(Util.convertSecondsToMinutes(totalSeconds));
        log.info("employee : " + value.get(0).getEmpId() + " total present time : " + totalTime + " and in seconds : " + totalSeconds);
        return new EmployeePresentTimeResponse(value.get(0).getEmpId(), totalTime, LocalDate.now(), Util.dailyAttendance(totalTime.getHour()));
    }

    private void validateEmpDetails(int empId) {
        if (employeeRepository.findById(empId).orElse(null) == null) {
            log.error("employee not found employee id : " + empId);
            throw new EmployeeNotFoundException("Employee not found in our system");
        }
    }
}
