package com.platform.biometric.repository;

import com.platform.biometric.model.entity.EmployeeTimeTrace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface BiometricRepository extends JpaRepository<EmployeeTimeTrace, Integer> {
    //List<EmployeeTimeTrace> findByEmpId(int empId);

    List<EmployeeTimeTrace> findByEmpIdAndDate(int empId, LocalDate date);

    List<EmployeeTimeTrace> findByDate(LocalDate date);
}
